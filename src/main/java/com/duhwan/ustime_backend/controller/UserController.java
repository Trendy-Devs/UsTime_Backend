package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.LoginDto;
import com.duhwan.ustime_backend.dto.UserDto;
import com.duhwan.ustime_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name ="User API", description ="유저 관련 기능을 제공하는 API")
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
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        String token = userService.login(dto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/userinfo")
    @Operation(summary = "유저 정보조회")
    public ResponseEntity<UserDto> getUserInfo(@RequestParam long userId) {
        UserDto userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

}
