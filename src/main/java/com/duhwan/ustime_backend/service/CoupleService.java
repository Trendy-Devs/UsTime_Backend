package com.duhwan.ustime_backend.service;

import com.duhwan.ustime_backend.config.security.CustomUserDetails;
import com.duhwan.ustime_backend.dao.CoupleMapper;
import com.duhwan.ustime_backend.dto.CoupleResponseDto;
import com.duhwan.ustime_backend.dto.CoupleDto;
import com.duhwan.ustime_backend.dto.CoupleRequestDto;
import com.duhwan.ustime_backend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoupleService {

    private final CoupleMapper mapper;

    // 커플 신청
    public void createCoupleRequest(CoupleRequestDto dto) {
        List<CoupleResponseDto> existRequests = mapper.getCoupleRequests(dto.getToUserId());
        if (!existRequests.isEmpty()) {
            throw new IllegalArgumentException("이미 대기 중인 신청이 있습니다.");
        }
        mapper.createCoupleRequest(dto);
    }

    // 커플 신청 조회
    public List<CoupleResponseDto> getCoupleRequest(Long userId) {
        return mapper.getCoupleRequests(userId);
    }

    // 커플 신청 승인
    @Transactional
    public void approveCoupleRequest(Long requestId) {
        CoupleRequestDto request = mapper.getRequestById(requestId);
        if (request == null || !"대기".equals(request.getStatus())) {
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        CoupleDto coupleDto = new CoupleDto();
        coupleDto.setUser1Id(request.getFromUserId());
        coupleDto.setUser2Id(request.getToUserId());
        mapper.createCouple(coupleDto);

        mapper.approveCoupleRequest(requestId);


        Long coupleId = coupleDto.getCoupleId();
        mapper.updateCoupleId(Map.of(
                "userId", request.getFromUserId(),
                "coupleId", coupleId
        ));
        mapper.updateCoupleId(Map.of(
                "userId", request.getToUserId(),
                "coupleId", coupleId
        ));

        // SecurityContext에 저장된 UserDetails 갱신
        updateUserDetailsWithCoupleId(request.getFromUserId(), coupleId);
        updateUserDetailsWithCoupleId(request.getToUserId(), coupleId);
    }

    // 커플 신청 거절
    public void declineCoupleRequest(Long requestId) {
        mapper.declineCoupleRequest(requestId);
    }

    // 유저 찾기
    public List<UserDto> searchUsers(String name) {
        List<UserDto> users = mapper.searchUsers(name);  // 유저 검색
        return users.stream()
                .map(user -> UserDto.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .build())
                .collect(Collectors.toList());
    }

    private void updateUserDetailsWithCoupleId(Long userId, Long coupleId) {
        // SecurityContext에서 현재 로그인된 사용자 정보 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 로그인된 사용자가 해당 userId와 일치하는지 확인
        if (userDetails.getUserId().equals(userId)) {
            userDetails.setCoupleId(coupleId);

            // 새로운 Authentication 객체 생성하여 SecurityContext에 업데이트
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new IllegalArgumentException("로그인된 사용자만 coupleId를 업데이트할 수 있습니다.");
        }
    }


}
