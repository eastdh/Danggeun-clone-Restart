package io.github.restart.gmo_danggeun.service.image;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
@Service
public class S3Service {

  private final S3Client s3Client;
  private final String bucketName;

  public S3Service(
      @Value("${aws.access-key-id}") String accessKey,
      @Value("${aws.secret-access-key}") String secretKey,
      @Value("${aws.region}") String region,
      @Value("${aws.s3.bucket-name}") String bucketName) {

    this.bucketName = bucketName;

    AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

    this.s3Client = S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
  }

  public String uploadFile(MultipartFile file, String filePath) throws IOException {
    String fileName = generateFileName(filePath, file.getOriginalFilename());

    PutObjectRequest request = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(fileName)
        .contentType(file.getContentType())
        .build();

    s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
    return getFileUrl(fileName);
  }

  public String getFileUrl(String fileName) {
    S3Utilities s3Utilities = s3Client.utilities();
    GetUrlRequest request = GetUrlRequest.builder()
        .bucket(bucketName)
        .key(fileName)
        .build();

    return s3Utilities.getUrl(request).toString();
  }

  public void deleteFile(String fileName) {
    DeleteObjectRequest request = DeleteObjectRequest.builder()
        .bucket(bucketName)
        .key(fileName)
        .build();

    s3Client.deleteObject(request);
  }

  public List<String> listFiles() {
    ListObjectsV2Request request = ListObjectsV2Request.builder()
        .bucket(bucketName)
        .build();

    ListObjectsV2Response response = s3Client.listObjectsV2(request);

    return response.contents().stream()
        .map(S3Object::key)
        .toList();
  }

  private String generateFileName(String filePath, String originalFilename) {
    return filePath + "/" + UUID.randomUUID().toString() + "-" + originalFilename;
  }
}
