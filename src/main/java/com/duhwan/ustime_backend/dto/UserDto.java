package com.duhwan.ustime_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "이메일은 비울수 없습니다.")
    private String email;               //이메일

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "패스워드는 최소 6자리 입니다.")
    private String password;            //비밀번호

    @NotBlank(message = "이름은 비울수 없습니다.")
    private String name;                //이름

    @NotBlank(message = "생일은 비울수 없습니다.")
    private LocalDate birthdate;        //생년월일

    @NotBlank(message = "성별은 비울수 없습니다")
    private String gender;              //성별

    @NotBlank(message = "전화번호는 비울수 없습니다.")
    private String phone;               //전화번호

    private Long coupleId;              //커플 고유 ID
    private LocalDateTime createdAt;    //생성일
    private String profileUrl;          //프로필 url

}
