package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.Photo.PhotoRequestDto;
import com.duhwan.ustime_backend.dto.Photo.PhotoResponseDto;
import com.duhwan.ustime_backend.dto.Photo.RandomPhotoDto;
import com.duhwan.ustime_backend.service.PhotoService;
import com.duhwan.ustime_backend.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("photo")
@RequiredArgsConstructor
@Tag(name="사진첩 API", description = "사진첩 관련 기능을 제공하는 API")
public class PhotoController {

    private final PhotoService photoService;


    @Operation(summary = "전체 사진조회")
    @GetMapping("/all")
    public ResponseEntity<List<PhotoResponseDto>> getAllPhotos(@RequestParam Long coupleId) {
        List<PhotoResponseDto> photos = photoService.getAllPhotos(coupleId);
        return ResponseEntity.ok(photos);
    }

    @Operation(summary = "단일 사진조회")
    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoResponseDto> getPhotoById(@PathVariable Long photoId) {
        PhotoResponseDto photo = photoService.getPhotoById(photoId);
        return ResponseEntity.ok(photo);
    }

    @Operation(summary = "저번달 랜덤사진 조회")
    @GetMapping("/random")
    public ResponseEntity<List<RandomPhotoDto>> getRandomLastMonthPhotos() {
        List<RandomPhotoDto> photos = photoService.getRandomLastMonthPhotos();
        return ResponseEntity.ok(photos);
    }

    @Operation(summary = "사진 등록")
    @PostMapping(value = "insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> insertPhoto(
            @RequestPart(value = "photo") PhotoRequestDto photo, // photo를 JSON으로 받기
            @RequestPart(value = "file") MultipartFile file) throws FileUploadException {

        photoService.insertPhoto(photo, file);
        return ResponseEntity.ok("사진 등록이 성공하였습니다.");
    }




    @Operation(summary = "사진 수정")
    @PutMapping("/update")
    public ResponseEntity<String> updatePhoto(@RequestBody PhotoRequestDto photo) {
        photoService.updatePhoto(photo);
        return ResponseEntity.ok("사진이 수정되었습니다.");
    }

    @Operation(summary = "사진 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePhoto(Long photoId) {
        photoService.deletePhoto(photoId);
        return ResponseEntity.ok("사진이 삭제되었습니다.");
    }

}
