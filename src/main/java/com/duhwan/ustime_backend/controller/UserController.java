package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.LoginDto;
import com.duhwan.ustime_backend.dto.UserDto;
import com.duhwan.ustime_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService service;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserDto dto) {
        service.signUp(dto); // 사용자 등록 처리
        return ResponseEntity.ok().build(); // 상태 코드 200 OK만 반환
    }

    @GetMapping("/selectone")
    public ResponseEntity<UserDto> selectOne(@RequestParam long idx) {
        UserDto dto = service.selectUser(idx);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        String token = userService.login(dto);
        return ResponseEntity.ok(token);
    }

}
