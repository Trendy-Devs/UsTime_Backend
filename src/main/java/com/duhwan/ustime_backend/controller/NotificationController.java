package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.Couple.CoupleResponseDto;
import com.duhwan.ustime_backend.dto.NotificationDto;
import com.duhwan.ustime_backend.dto.ScheduleDto;
import com.duhwan.ustime_backend.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name ="알림 API", description ="알림 관련 기능을 제공하는 API")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/getNotify")
    @Operation(summary = "알림 조회")
    public ResponseEntity<List<NotificationDto>> getNotifications(@RequestParam Long userId) {
        List<NotificationDto> notifications = notificationService.getNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/getDetail")
    @Operation(summary = "요청받은 커플/일정에 따라 DTO 반환")
    @ApiResponse(responseCode = "200", description = "Success",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CoupleResponseDto.class)),
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleDto.class))
            })
    public ResponseEntity<?> getDetail(@RequestParam String type, @RequestParam Long typeId) {
        Object result = notificationService.getDetail(type, typeId);

        if (result instanceof CoupleResponseDto) {
            return ResponseEntity.ok((CoupleResponseDto) result);
        } else if (result instanceof ScheduleDto) {
            return ResponseEntity.ok((ScheduleDto) result);
        } else {
            throw new IllegalArgumentException("잘못된 타입 요청: " + type);
        }
    }

    @PutMapping("/markAsRead")
    @Operation(summary = "안읽음 처리")
    public ResponseEntity<String> markAsRead(@RequestParam Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("알림이 읽음으로 처리되었습니다.");
    }

    @DeleteMapping("/delete")
    @Operation(summary = "알림 삭제")
    public ResponseEntity<String> deleteNotification(@RequestParam Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("알림이 삭제되었습니다.");
    }

    @DeleteMapping("/old")
    @Operation(summary = "3일/30일 알림 자동삭제(스케줄러 확인용)")
    public ResponseEntity<String> deleteOldNoti() {
        int result = notificationService.deleteOldNoti();
        return ResponseEntity.ok(result + "개가 삭제되었습니다.");
    }

}
