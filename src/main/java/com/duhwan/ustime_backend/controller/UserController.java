package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.ChangePasswordDto;
import com.duhwan.ustime_backend.dto.LoginDto;
import com.duhwan.ustime_backend.dto.UserDto;
import com.duhwan.ustime_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name ="유저 API", description ="유저 관련 기능을 제공하는 API")
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<Void> signup(@RequestBody UserDto dto) {
        userService.signUp(dto); // 사용자 등록 처리
        return ResponseEntity.ok().build(); // 상태 코드 200 OK만 반환
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<Map<String,Object>> login(@RequestBody LoginDto dto) {
        Map<String,Object> result = userService.login(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/userinfo")
    @Operation(summary = "유저 정보조회")
    public ResponseEntity<UserDto> getUserInfo(@RequestParam long userId) {
        UserDto userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

    @PutMapping("/update")
    @Operation(summary = "유저 정보수정")
    public ResponseEntity<String> updateUserInfo(@RequestBody UserDto dto) {
        userService.updateUserInfo(dto);
        return ResponseEntity.ok("유저 정보수정이 완료되었습니다.");
    }

    @PutMapping("/changePassword")
    @Operation(summary = "비밀번호 변경하기")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        try {
            userService.changePassword(changePasswordDto);
            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패");
        }
    }
    
    @PostMapping(value = "/profile" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "프로필 사진 설정")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("userId") Long userId, @RequestPart(value = "file") MultipartFile file) {   //requestPart 사용해야함
        try {
            // MIME 타입 검증
            String contentType = file.getContentType();
            if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
                return ResponseEntity.badRequest().body("허용되지 않는 파일 형식입니다. JPEG 또는 PNG만 가능합니다.");
            }

            // 파일 크기 제한 (5MB 이하)
            final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
            if (file.getSize() > MAX_FILE_SIZE) {
                return ResponseEntity.badRequest().body("파일 크기는 5MB를 초과할 수 없습니다.");
            }

            String imageUrl = userService.updateUserProfileImage(userId, file);
            return ResponseEntity.ok(Map.of("profileUrl", imageUrl));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("프로필 사진 업로드 실패");
        }
    }
    
}
