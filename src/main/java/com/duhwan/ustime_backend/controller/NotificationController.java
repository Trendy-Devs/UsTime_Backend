package com.duhwan.ustime_backend.controller;

import com.duhwan.ustime_backend.dto.NotificationDto;
import com.duhwan.ustime_backend.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
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

    // 알림 조회 API
    @GetMapping("/getNotify")
    @Operation(summary = "알림 조회")
    public ResponseEntity<List<NotificationDto>> getNotifications(@RequestParam Long userId) {
        List<NotificationDto> notifications = notificationService.getNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // 알림 읽음 처리
    @PutMapping("/markAsRead")
    @Operation(summary = "안읽음 처리")
    public ResponseEntity<String> markAsRead(@RequestParam Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("알림이 읽음으로 처리되었습니다.");
    }

    // 알림 삭제
    @DeleteMapping("/delete")
    @Operation(summary = "알림 삭제")
    public ResponseEntity<String> deleteNotification(@RequestParam Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("알림이 삭제되었습니다.");
    }
}
