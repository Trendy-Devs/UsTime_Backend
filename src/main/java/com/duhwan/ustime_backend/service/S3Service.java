package com.duhwan.ustime_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png");

    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    @Value("${cloudfront.domain-name}")
    private String cloudFrontDomainName;

    // 파일 검사 메서드
    public void validateFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (!ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식입니다. JPEG 또는 PNG만 가능합니다.");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("파일 크기는 5MB를 초과할 수 없습니다.");
        }
    }

    // 파일 업로드 메서드
    public String uploadFile(MultipartFile file, String oldFileUrl) throws IOException{

        // 이전 파일 삭제
        if (oldFileUrl != null && !oldFileUrl.isBlank()) {
            deleteFile(oldFileUrl);
        }

        // 1. 파일 이름을 고유하게 생성
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new IllegalArgumentException("파일 이름이 유효하지 않습니다.");
        }
        // UUID를 활용하여 고유한 파일 이름 생성
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        String fileUrl = "userImage/" + uniqueFileName;
        // 2. S3로 파일 업로드
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileUrl)
                    .contentType(file.getContentType()) // MIME 타입 설정
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        } catch (SdkException e) {
            throw new IOException("S3 업로드 중 오류가 발생했습니다.", e);
        }
        return getFileUrl(fileUrl);
    }

    // 파일 삭제 메서드
    public void deleteFile(String fileUrl) {
        try {
            // fileUrl에서 key 추출 (cloudFrontDomainName 이후 경로만 사용)
            String key = fileUrl.replace(cloudFrontDomainName + "/", "");
            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key).build());
        } catch (SdkException e) {
            throw new RuntimeException("S3에서 파일 삭제 중 오류가 발생했습니다.", e);
        }
    }

    // 파일 다운로드 메서드 (CloudFront에서 직접 url을 받음)
    public String getFileUrl(String fileUrl) {
        return cloudFrontDomainName + "/" + fileUrl;
    }


    // 파일 다운로드 메서드(로컬에서 직접 받을 떄) -> 현재 사용하지 않음
    public Path downloadFile(String fileName) throws IOException {
        // S3에서 파일을 가져옵니다.
        // 여기서 AWS SDK 2.x 버전에서 getObject() 메서드가 반환하는 타입이 S3Object -> GetObjectResponse로 변경되었습니다.
        ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build());

        // 파일을 저장할 경로 설정
        Path path = Paths.get("/path/to/save/" + fileName);

        // 스트리밍 방식으로 파일을 로컬 파일 시스템에 저장
        Files.copy(s3Object, path);

        // 반환된 파일 경로를 리턴
        return path;
    }

}
