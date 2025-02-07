package com.duhwan.ustime_backend.dto.Photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("RandomPhotoDto")
// 랜덤사진 응답 Dto
public class RandomPhotoDto {

    private Long photoId;       // 사진 ID
    private String photoUrl;    //사진 URL
}
