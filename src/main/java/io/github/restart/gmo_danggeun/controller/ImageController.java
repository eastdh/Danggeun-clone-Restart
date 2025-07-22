package io.github.restart.gmo_danggeun.controller;

import io.github.restart.gmo_danggeun.dto.image.ImageDto;
import io.github.restart.gmo_danggeun.entity.Image;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.security.CustomUserDetails;
import io.github.restart.gmo_danggeun.service.image.ImageService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

  private final ImageService imageService;

  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getImage(
      @PathVariable Long id
  ) {
    Optional<Image> imageOptional = imageService.getImage(id);
    ImageDto dto = imageOptional.map(imageService::convertImageToDto).orElse(null);
    return dto != null ?
        ResponseEntity.ok(dto)
        :ResponseEntity.notFound().build();
  }

  @GetMapping("/list")
  public ResponseEntity<List<ImageDto>> listImages(
      @RequestBody List<Long> idList
  ) {
    List<Image> imageList = imageService.getAllImagesById(idList);
    return ResponseEntity.ok(imageService.convertImagesToDtos(imageList));
  }

  @PostMapping("/upload")
  public ResponseEntity<?> uploadImages(
      @RequestParam("files") List<MultipartFile> files,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    User user = userDetails.getUser();
    try {
      List<Image> imageList = imageService.uploadImage(files, user);
      List<Long> imagesId = imageService.getImagesId(imageList);
      return ResponseEntity.ok().body(imagesId);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteImage(
      @PathVariable Long id
  ) {
    try {
      imageService.deleteImage(id);
      return ResponseEntity.ok("파일 삭제 성공");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("파일 삭제 실패");
    }
  }
}
