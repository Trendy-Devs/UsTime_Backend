package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.Photo.PhotoRequestDto;
import com.duhwan.ustime_backend.dto.Photo.PhotoResponseDto;
import com.duhwan.ustime_backend.dto.Photo.RandomPhotoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PhotoMapper {
    List<PhotoResponseDto> getAllPhotos(@Param("coupleId") Long coupleId);
    PhotoResponseDto getPhotoById(@Param("photoId") Long photoId);
    List<RandomPhotoDto> getRandomLastMonthPhotos();
    void insertPhoto(PhotoRequestDto photo);
    void updatePhoto(PhotoRequestDto photo);
    void deletePhoto(@Param("photoId") Long photoId);
}
