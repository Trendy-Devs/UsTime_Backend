package com.duhwan.ustime_backend.dto.Photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("PhotoRequestDto")
// 사진 요청 Dto
public class PhotoRequestDto {

    private Long coupleId;     // 커플 ID
    private Long uploadedBy;   // 업로더 ID
    private String photoUrl;   // 사진 URL (S3)
    private String photoTitle; // 사진 제목
    private String caption;    // 사진 설명 (옵션)
}