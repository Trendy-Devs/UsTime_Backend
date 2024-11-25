package com.duhwan.ustime_backend.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoupleDto {

    private Long coupleId; // 커플 ID
    private Long user1Id; // 사용자 1 (남자친구)
    private Long user2Id; // 사용자 2 (여자친구)
    private LocalDate startDate; // 시작일 (커플 시작일)
    private LocalDate anniversary; // 기념일 (옵션)

    //디버깅용
    @Override
    public String toString() {
        return "CoupleDTO{" +
                "coupleId=" + coupleId +
                ", user1Id=" + user1Id +
                ", user2Id=" + user2Id +
                ", startDate=" + startDate +
                ", anniversary=" + anniversary +
                '}';
    }
}
