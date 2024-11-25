package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.CoupleResponseDto;
import com.duhwan.ustime_backend.dto.CoupleRequestDto;
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
@Tag(name ="Couple API", description ="커플 관련 기능을 제공하는 API")
public class CoupleController {

    private final CoupleService service;

    @PostMapping("/request")
    @Operation(summary = "커플 신청")
    public ResponseEntity<String> createCoupleRequest(@RequestParam Long fromUserId, @RequestParam Long toUserId) {
        CoupleRequestDto dto = new CoupleRequestDto();
        dto.setFromUserId(fromUserId);
        dto.setToUserId(toUserId);
        service.createCoupleRequest(dto);
        return ResponseEntity.ok("커플 신청이 성공적으로 추가되었습니다.");
    }

    @GetMapping("/getrequest")
    @Operation(summary = "커플 신청조회")
    public ResponseEntity<List<CoupleResponseDto>> getCoupleRequest(@RequestParam Long userId) {
        List<CoupleResponseDto> getRequest = service.getCoupleRequest(userId);
        return ResponseEntity.ok(getRequest);
    }

    @Operation(summary = "커플 승인")
    @PutMapping("/approve")
    public ResponseEntity<String> approveCoupleRequest(@RequestParam Long requestId) {
        service.approveCoupleRequest(requestId);
        return ResponseEntity.ok("커플 신청이 승인 되었습니다.");
    }

    @Operation(summary = "커플 거절")
    @PutMapping("/decline")
    public ResponseEntity<String> declineCoupleReqest(@RequestParam Long requestId) {
        service.declineCoupleRequest(requestId);
        return ResponseEntity.ok("커플 신청이 거절되었습니다.");
    }
}
