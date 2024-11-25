package com.duhwan.ustime_backend.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Long userId;                //유저 ID
    private String email;               //이메일
    private String password;            //비밀번호
    private String name;                //이름
    private LocalDate birthdate;        //생년월일
    private String gender;              //성별
    private String phone;               //전화번호
    private Long coupleId;              //커플 고유 ID
    private LocalDateTime createdAt;    //생성일

    public static UserDto create(String email, String password, String name, Long coupleId,
                                 LocalDate birthdate, String gender, String phone) {
        return UserDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .coupleId(coupleId)
                .birthdate(birthdate)
                .gender(gender)
                .phone(phone)
                .createdAt(LocalDateTime.now()) // 현재 시간으로 생성일 설정
                .build();
    }

}
