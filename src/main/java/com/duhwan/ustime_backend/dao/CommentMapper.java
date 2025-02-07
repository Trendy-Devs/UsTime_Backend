package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.Photo.CommentResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentResponseDto> getCommentsByPhotoId(@Param("photoId") Long photoId);
    void insertComment(CommentResponseDto comment);
    void updateComment(CommentResponseDto comment);
    void deleteComment(@Param("commentId") Long commentId);
}
