package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.dao.CoupleMapper;
import com.duhwan.ustime_backend.dao.NotificationMapper;
import com.duhwan.ustime_backend.dao.UserMapper;
import com.duhwan.ustime_backend.dto.CoupleDto;
import com.duhwan.ustime_backend.dto.CoupleRequestDto;
import com.duhwan.ustime_backend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoupleService {

    private final CoupleMapper coupleMapper;
    private final NotificationService notificationService;
    private final UserMapper userMapper;
    private final NotificationMapper notificationMapper;

    // 커플 신청
    public void createCoupleRequest(CoupleRequestDto dto) {
        Long existRequests = coupleMapper.getCoupleRequests(dto.getToUserId());
        if (existRequests != null) {
            throw new IllegalArgumentException("이미 대기 중인 신청이 있습니다.");
        }
        coupleMapper.createCoupleRequest(dto);

        // DTO에서 자동으로 생성된 requestId 값을 가져오기
        Long requestId = dto.getRequestId();
        String reqeustName = userMapper.selectUser(dto.getFromUserId()).getName();

        // 알림 생성: 상대방에게 알림
        notificationService.createNotification(dto.getToUserId(), "커플 신청", requestId, "새로운 커플 요청이 왔습니다.","✨ " + reqeustName+ "님 으로부터 커플 요청이 왔습니다. ✨\\n수락하여 특별한 인연을 시작해보세요. ❤", null);
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

        String requestName = userMapper.selectUser(request.getFromUserId()).getName();
        String responseName = userMapper.selectUser(request.getToUserId()).getName();

        String summary = "✨ 두근두근, "+ requestName +"님과 "+ responseName +"님의 인연이 시작되었습니다! ✨\\n이제 USTime에서 서로의 일상을 공유하고 특별한 순간을 만들어보세요.❤";


        // 알림 생성: 신청자와 승인자에게 알림
        notificationService.createNotification(request.getFromUserId(), "커플 승인", requestId,"커플 매칭되었습니다.",summary ,coupleId);
        notificationService.createNotification(request.getToUserId(), "커플 승인", requestId,"커플 매칭되었습니다.", summary ,coupleId);

        // 매칭 후 커플 신청 알림 삭제
        notificationMapper.deleteCoupleRequestNoti(requestId);

    }

    // 커플 신청 거절
    @Transactional
    public void declineCoupleRequest(Long requestId) {
        CoupleRequestDto request = coupleMapper.getRequestById(requestId);
        if (request == null || !"대기".equals(request.getStatus())) {
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        coupleMapper.declineCoupleRequest(requestId);

        String summary = "아쉽게도 요청이 거절되었습니다.\\n" +
                "더 멋진 인연이 기다리고 있을 거예요!";
        // 알림 생성: 거절자와 신청자에게 알림
        notificationService.createNotification(request.getToUserId(), "커플 거절", requestId, "커플 요청이 거절되었습니다.",summary ,null);

        // 거절 후 요청 알림 삭제
        notificationMapper.deleteCoupleRequestNoti(requestId);

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

    // 커플 유무 찾기
    public boolean existCouple(Long userId) {
        Long existCouple = userMapper.getCoupleId(userId);
        return existCouple != null;
    }

    // 커플 해지
    @Transactional
    public void deleteCouple(Long coupleId) {
        CoupleDto couple = coupleMapper.getCoupleById(coupleId);
        if (couple == null) {
            throw new IllegalArgumentException("커플을 찾을 수 없습니다.");
        }
        coupleMapper.deleteCouple(coupleId);
    }

    // 기념일 수정
    @Transactional
    public void updateAnniversary(Long coupleId, LocalDate anniversary) {
        coupleMapper.updateAnniversary(coupleId,anniversary);
    }

    // 커플 정보보기
    public CoupleDto getCoupleInfo(Long coupleId) {
        return coupleMapper.getCoupleById(coupleId);
    }

}
