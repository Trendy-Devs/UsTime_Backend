package com.duhwan.ustime_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListDto {

    private Long checklistId;
    private Long userId;
    private Long coupleId;
    private String category;
    private String title;
    private boolean isChecked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
