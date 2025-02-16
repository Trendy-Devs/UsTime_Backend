package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.CoupleMapper;
import com.duhwan.ustime_backend.dao.PhotoMapper;
import com.duhwan.ustime_backend.dto.Photo.PhotoRequestDto;
import com.duhwan.ustime_backend.dto.Photo.PhotoResponseDto;
import com.duhwan.ustime_backend.dto.Photo.RandomPhotoDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoMapper photoMapper;
    private final NotificationService notificationService;
    private final S3Service s3Service;
    private final UserService userService;
    private final CoupleMapper coupleMapper;

    public List<PhotoResponseDto> getAllPhotos(Long coupleId) {
        List<PhotoResponseDto> photos = photoMapper.getAllPhotos(coupleId);
        return photos;
    }

    public PhotoResponseDto getPhotoById(Long photoId) {
        PhotoResponseDto photo = photoMapper.getPhotoById(photoId);
        return photo;
    }

    public List<RandomPhotoDto> getRandomLastMonthPhotos() {
        return photoMapper.getRandomLastMonthPhotos();
    }

    @Transactional
    public void insertPhoto(PhotoRequestDto photo, MultipartFile file) throws FileUploadException {
        s3Service.validateFile(file);
        String photoUrl;
        try {
            photoUrl = s3Service.uploadFile(file, null);
        } catch (IOException e) {
            throw new FileUploadException("사진 업로드 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
        photo.setPhotoUrl(photoUrl);
        photoMapper.insertPhoto(photo);

    }

    @Transactional
    public void updatePhoto(PhotoRequestDto photo) {
        photoMapper.updatePhoto(photo);
    }

    @Transactional
    public void deletePhoto(Long photoId, Long loggedInUserId) {
        Long uploadedBy = photoMapper.findPhotoById(photoId);
        if (!uploadedBy.equals(loggedInUserId)) {
            throw new AccessDeniedException("본인이 업로드한 사진만 삭제할 수 있습니다.");
        }
        photoMapper.deletePhoto(photoId, uploadedBy);
    }



}
