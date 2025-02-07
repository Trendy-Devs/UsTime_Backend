package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.Couple.CoupleDto;
import com.duhwan.ustime_backend.dto.Couple.CoupleRequestDto;
import com.duhwan.ustime_backend.dto.UserDto;
import com.duhwan.ustime_backend.service.CoupleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/couple")
@Tag(name ="커플 API", description ="커플 관련 기능을 제공하는 API")
public class CoupleController {

    private final CoupleService coupleService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/request")
    @Operation(summary = "커플 신청")
    public ResponseEntity<String> createCoupleRequest(@RequestParam Long fromUserId, @RequestParam Long toUserId) {
        if (coupleService.existCouple(fromUserId) || coupleService.existCouple(toUserId)) {
            return ResponseEntity.badRequest().body("이미 커플 상태입니다. 요청을 보낼 수 없습니다.");
        }
        CoupleRequestDto dto = new CoupleRequestDto();
        dto.setFromUserId(fromUserId);
        dto.setToUserId(toUserId);
        coupleService.createCoupleRequest(dto);
        return ResponseEntity.ok("커플 신청이 성공적으로 추가되었습니다.");
    }

    @PutMapping("/approve")
    @Operation(summary = "커플 승인")
    public ResponseEntity<String> approveCoupleRequest(@RequestParam Long requestId) {
        coupleService.approveCoupleRequest(requestId);
        return ResponseEntity.ok("커플 신청이 승인되었습니다.");
    }

    @PutMapping("/decline")
    @Operation(summary = "커플 거절")
    public ResponseEntity<String> declineCoupleRequest(@RequestParam Long requestId) {
        coupleService.declineCoupleRequest(requestId);
        return ResponseEntity.ok("커플 신청이 거절되었습니다.");
    }

    @GetMapping("/search")
    @Operation(summary = "유저 검색")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String name) {
        List<UserDto> users = coupleService.searchUsers(name);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "커플 삭제")
    public ResponseEntity<String> deleteCouple(@RequestParam Long coupleId) {
        coupleService.deleteCouple(coupleId);
        return ResponseEntity.ok("커플이 삭제 되었습니다.");
    }

    @PostMapping("/update")
    @Operation(summary = "기념일 수정")
    public ResponseEntity<String> updateAnniversary(@RequestParam Long coupleId ,@RequestParam String date) {
        LocalDate anniversary = LocalDate.parse(date);
        coupleService.updateAnniversary(coupleId,anniversary);
        return ResponseEntity.ok("커플 기념일이 수정되었습니다.");
    }

    @GetMapping("/getInfo")
    @Operation(summary = "커플 정보보기")
    public ResponseEntity<CoupleDto> getCoupleInfo(@RequestParam Long coupleId) {
        CoupleDto info = coupleService.getCoupleInfo(coupleId);
        return ResponseEntity.ok(info);
    }
}
