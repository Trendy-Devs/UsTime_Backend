package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.CoupleRequestDto;
import com.duhwan.ustime_backend.dto.UserDto;
import com.duhwan.ustime_backend.service.CoupleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/couple")
@Tag(name ="커플 API", description ="커플 관련 기능을 제공하는 API")
public class CoupleController {

    private final CoupleService coupleService;

    // 커플 신청
    @PostMapping("/request")
    @Operation(summary = "커플 신청")
    public ResponseEntity<String> createCoupleRequest(@RequestParam Long fromUserId, @RequestParam Long toUserId) {
        CoupleRequestDto dto = new CoupleRequestDto();
        dto.setFromUserId(fromUserId);
        dto.setToUserId(toUserId);
        coupleService.createCoupleRequest(dto);
        return ResponseEntity.ok("커플 신청이 성공적으로 추가되었습니다.");
    }

    // 커플 신청 승인
    @PutMapping("/approve")
    @Operation(summary = "커플 승인")
    public ResponseEntity<String> approveCoupleRequest(@RequestParam Long requestId) {
        coupleService.approveCoupleRequest(requestId);
        return ResponseEntity.ok("커플 신청이 승인되었습니다.");
    }

    // 커플 신청 거절
    @PutMapping("/decline")
    @Operation(summary = "커플 거절")
    public ResponseEntity<String> declineCoupleRequest(@RequestParam Long requestId) {
        coupleService.declineCoupleRequest(requestId);
        return ResponseEntity.ok("커플 신청이 거절되었습니다.");
    }

    // 유저 검색
    @GetMapping("/search")
    @Operation(summary = "유저 검색")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String name) {
        List<UserDto> users = coupleService.searchUsers(name);
        return ResponseEntity.ok(users);
    }
}
