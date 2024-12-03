package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.CoupleMapper;
import com.duhwan.ustime_backend.dto.CoupleDto;
import com.duhwan.ustime_backend.dto.CoupleRequestDto;
import com.duhwan.ustime_backend.dto.CoupleResponseDto;
import com.duhwan.ustime_backend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoupleService {

    private final CoupleMapper coupleMapper;
    private final NotificationService notificationService;

    // 커플 신청
    public void createCoupleRequest(CoupleRequestDto dto) {
        List<CoupleResponseDto> existRequests = coupleMapper.getCoupleRequests(dto.getToUserId());
        if (!existRequests.isEmpty()) {
            throw new IllegalArgumentException("이미 대기 중인 신청이 있습니다.");
        }
        coupleMapper.createCoupleRequest(dto);

        // DTO에서 자동으로 생성된 requestId 값을 가져오기
        Long requestId = dto.getRequestId();

        // 알림 생성: 상대방에게 알림
        notificationService.createNotification(dto.getToUserId(), "커플 신청", "새로운 커플 요청이 왔습니다.");
    }

    // 커플 신청 승인
    @Transactional
    public void approveCoupleRequest(Long requestId) {
        CoupleRequestDto request = coupleMapper.getRequestById(requestId);
        if (request == null || !"대기".equals(request.getStatus())) {
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        CoupleDto coupleDto = new CoupleDto();
        coupleDto.setUser1Id(request.getFromUserId());
        coupleDto.setUser2Id(request.getToUserId());
        coupleMapper.createCouple(coupleDto);

        Long coupleId = coupleDto.getCoupleId();
        coupleMapper.approveCoupleRequest(requestId);

        coupleMapper.updateCoupleId(Map.of(
                "userId", request.getFromUserId(),
                "coupleId", coupleId
        ));
        coupleMapper.updateCoupleId(Map.of(
                "userId", request.getToUserId(),
                "coupleId", coupleId
        ));

        // 알림 생성: 신청자와 승인자에게 알림
        notificationService.createNotification(request.getFromUserId(), "커플 승인", "커플 요청이 승인되었습니다.");
        notificationService.createNotification(request.getToUserId(), "커플 승인", "커플 요청이 승인되었습니다.");
    }

    // 커플 신청 거절
    public void declineCoupleRequest(Long requestId) {
        CoupleRequestDto request = coupleMapper.getRequestById(requestId);
        if (request == null || !"대기".equals(request.getStatus())) {
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        coupleMapper.declineCoupleRequest(requestId);

        // 알림 생성: 거절자와 신청자에게 알림
        notificationService.createNotification(request.getFromUserId(), "커플 거절", "커플 요청이 거절되었습니다.");
        notificationService.createNotification(request.getToUserId(), "커플 거절", "커플 요청이 거절되었습니다.");
    }

    // 유저 찾기
    public List<UserDto> searchUsers(String name) {
        List<UserDto> users = coupleMapper.searchUsers(name);  // 유저 검색
        return users.stream()
                .map(user -> UserDto.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
