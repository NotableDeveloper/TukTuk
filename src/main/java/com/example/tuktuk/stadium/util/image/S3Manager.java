package com.example.tuktuk.stadium.util.image;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Component
@Getter
@RequiredArgsConstructor
public class S3Manager implements ObjectStorageFunction {
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private S3Client s3Client;

    @PostConstruct
    public void setS3Client() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        s3Client = S3Client.builder()
                .credentialsProvider(staticCredentialsProvider)
                .region(Region.of(region))
                .build();
    }

    @Override
    public String putObject(MultipartFile file) {
        String savedObjectName = "";

        try {
            savedObjectName = createFileName(file.getOriginalFilename());

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(savedObjectName)
                    .build();

            s3Client.putObject(request, convertMultipartFileToPath(file));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 변환 중에 에러가 발생하였습니다.");
        }

        return savedObjectName;
    }

    @Override
    public String getObject(String objectName) {
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        return String.valueOf(s3Client.utilities().getUrl(request));
    }

    @Override
    public void deleteObject(String objectUrl) {
        String objectName = extractObjectNameFromURL(objectUrl);

        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();

            s3Client.deleteObject(deleteRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 삭제 중에 에러가 발생하였습니다.");
        }
    }

    private String createFileName(String originalFileName) {
        String fileExtension = getFileExtension(originalFileName);
        StringBuilder newFileName = new StringBuilder();

        newFileName.append(UUID.randomUUID().toString())
                .append(fileExtension);

        return newFileName.toString();
    }

    private Path convertMultipartFileToPath(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String tempDir = System.getProperty("java.io.tmpdir");
        File tempFile = File.createTempFile(UUID.randomUUID().toString(),
                fileExtension,
                new File(tempDir));
        file.transferTo(tempFile);

        return tempFile.toPath();
    }

    private String getFileExtension(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    private String extractObjectNameFromURL(String objectURL) {
        return objectURL.substring(objectURL.lastIndexOf("/") + 1);
    }
}
