package com.duhwan.ustime_backend.dao;

import com.duhwan.ustime_backend.dto.Photo.PhotoRequestDto;
import com.duhwan.ustime_backend.dto.Photo.PhotoResponseDto;
import com.duhwan.ustime_backend.dto.Photo.RandomPhotoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Mapper
public interface PhotoMapper {
    List<PhotoResponseDto> getAllPhotos(@Param("coupleId") Long coupleId);
    PhotoResponseDto getPhotoById(@Param("photoId") Long photoId);
    List<RandomPhotoDto> getRandomLastMonthPhotos(String startDate, String endDate);
    void insertPhoto(PhotoRequestDto photo);
    void updatePhoto(PhotoRequestDto photo);
    void deletePhoto(@Param("photoId") Long photoId,@Param("uploadedBy") Long uploadedBy);
    PhotoRequestDto findPhotoById(@Param("photoId") Long photoId);
}
