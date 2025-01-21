package com.duhwan.ustime_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListDto {

    private Long checklistId;               //체크리스트 아이디
    private Long userId;                    //유저아이디
    private Long coupleId;                  //커플 번호
    private String category;                //카테고리
    private String title;                   //제목
    @JsonProperty("isChecked")
    private boolean isChecked;              //체크 유무
    private LocalDateTime createdAt;        //생성일
    private LocalDateTime updatedAt;        //업데이트된 날짜
}
