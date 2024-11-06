package com.duhwan.ustime_backend.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CoupleDto {

    private Long coupleId;              //커플 고유 ID
    private Long user1Id;               //유저ID(남자친구)
    private Long user2Id;               //유저ID(여자친구)
    private LocalDate startDate;        //사귄날
    private LocalDate anniversary;      //기념일
}
