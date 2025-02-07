package com.duhwan.ustime_backend.dto.Photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Alias("PhotoResponseDto")
// 사진 응답 Dto
public class PhotoResponseDto {

    private Long photoId;       // 사진 ID
    private String photoUrl;    // 사진 URL
    private String photoTitle;  // 제목
    private String caption;     // 설명
    private String uploadedByUsername; // 업로더 이름
    private LocalDateTime uploadedAt;  // 업로드 시간
}




