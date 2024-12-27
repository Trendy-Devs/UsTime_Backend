package com.duhwan.ustime_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotiByCoupleIdDto {
    private NotificationDto notification;
    private Long coupleId;
}
