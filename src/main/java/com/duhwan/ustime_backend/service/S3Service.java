package com.duhwan.ustime_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    @Value("${cloudfront.domain-name}")
    private String cloudFrontDomainName;

    // 파일 업로드 메서드
    public String uploadFile(MultipartFile file) throws IOException{

        String fileName = file.getOriginalFilename();
        // S3로 파일 업로드
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build(),
                RequestBody.fromBytes(file.getBytes()));
        return getFileUrl(fileName);
    }

    // 파일 다운로드 메서드 (CloudFront에서 직접 url을 받음)
    public String getFileUrl(String fileName) {
        return "https://" + cloudFrontDomainName + "/" + fileName;
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
