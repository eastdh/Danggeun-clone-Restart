package io.github.restart.gmo_danggeun.service.image;

import io.github.restart.gmo_danggeun.dto.image.ImageDto;
import io.github.restart.gmo_danggeun.entity.Image;
import io.github.restart.gmo_danggeun.entity.ImageTrade;
import io.github.restart.gmo_danggeun.entity.Trade;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.entity.readonly.TradeImageList;
import io.github.restart.gmo_danggeun.repository.ImageChatRepository;
import io.github.restart.gmo_danggeun.repository.ImageRepository;
import io.github.restart.gmo_danggeun.repository.ImageTradeRepository;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
  private final S3Service s3Service;
  private final ImageRepository imageRepository;
  private final ImageTradeRepository imageTradeRepository;
  private final ImageChatRepository imageChatRepository;
  private static final Tika tika = new Tika();

  public ImageService(S3Service s3Service, ImageRepository imageRepository,
      ImageTradeRepository imageTradeRepository, ImageChatRepository imageChatRepository) {
    this.s3Service = s3Service;
    this.imageRepository = imageRepository;
    this.imageTradeRepository = imageTradeRepository;
    this.imageChatRepository = imageChatRepository;
  }

  @Transactional
  public List<Image> uploadImage(List<MultipartFile> files, User user) throws IOException {
      return files.stream()
          .filter(file -> !file.isEmpty())
          .filter(file -> {
            try {
              return isImage(file);
            } catch (IOException e) {
              return false;
            }
          })
          .map(file -> {
            try {
              String s3Url = s3Service.uploadFile(file);
              String s3Key = extractKeyFromUrl(s3Url);
              Image image = new Image(
                  user, s3Url, s3Key, LocalDateTime.now(), LocalDateTime.now().plusMonths(30)
              );
              return imageRepository.save(image);
            } catch (Exception e) {
              throw new RuntimeException("이미지 업로드 실패: " + file.getOriginalFilename());
            }
          })
          .toList();
  }

  @Transactional
  public List<ImageTrade> addImageTrade(Trade trade, List<Image> imageList) {
    return imageList.stream()
        .map(image -> {
          try {
            ImageTrade imageTrade = new ImageTrade(trade, image);
            return imageTradeRepository.save(imageTrade);
          } catch (Exception e) {
            throw new RuntimeException("거래글 이미지 정보 추가 실패");
          }
        })
        .toList();
  }

  @Transactional
  public List<ImageTrade> uploadTradeImages(List<MultipartFile> files, User user, Trade trade)
  throws IOException {
    List<Image> imageList = uploadImage(files, user);
    return addImageTrade(trade, imageList);
  }

  public List<Long> getImagesId(List<Image> imageList) {
    return imageList.stream()
        .map(Image::getId)
        .toList();
  }

  public List<Image> getAllImagesById(List<Long> idList) {
    return imageRepository.findAllById(idList);
  }

  public List<ImageDto> convertImagesToDtos(List<Image> imageList) {
    return imageList.stream()
        .map(image ->
            new ImageDto(
              image.getId(), image.getUploader().getId(),
              image.getUrl(), image.getCreatedAt(),
              image.getExpireDate()
            )
        )
        .toList();
  }

  public Optional<Image> getImage(Long id) {
    return imageRepository.findById(id);
  }

  public ImageDto convertImageToDto(Image image) {
    return new ImageDto(
        image.getId(), image.getUploader().getId(),
        image.getUrl(), image.getCreatedAt(),
        image.getExpireDate()
    );
  }

  @Transactional
  public void deleteImage(Long id) throws Exception {
    Optional<Image> imageOptional = imageRepository.findById(id);
    if (imageOptional.isPresent()) {
      Image image = imageOptional.get();
      s3Service.deleteFile(image.getS3key());
      imageRepository.deleteById(id);
    } else {
      throw new Exception("이미지를 찾을 수 없습니다");
    }
  }

  private static boolean isImage(MultipartFile file) throws IOException {
    try (InputStream inputStream = file.getInputStream()) {
      String mime = tika.detect(inputStream);
      return mime != null && mime.startsWith("image");
    } catch (IOException e) {
      return false;
    }
  }

  private String extractKeyFromUrl(String url) {
    return url.substring(url.lastIndexOf("/") + 1);
  }
}
