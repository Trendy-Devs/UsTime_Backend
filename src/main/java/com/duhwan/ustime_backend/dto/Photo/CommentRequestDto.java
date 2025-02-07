package com.duhwan.ustime_backend.dto.Photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("CommentRequestDto")
// 댓글 요청 Dto
public class CommentRequestDto {

    private Long photoId;     // 사진 ID
    private Long commentedBy; // 댓글 작성자 ID
    private String content;   // 댓글 내용
}
