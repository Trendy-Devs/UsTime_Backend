package com.duhwan.ustime_backend.dto.Photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("CommentResponseDto")
// 댓글 응답 Dto
public class CommentResponseDto {

    private Long commentId;         // 댓글 ID
    private Long photoId;           // 사진 ID
    private String commentedByUsername; // 작성자 이름
    private String content;         // 댓글 내용
    private LocalDateTime createdAt;// 댓글 작성 시간
}
