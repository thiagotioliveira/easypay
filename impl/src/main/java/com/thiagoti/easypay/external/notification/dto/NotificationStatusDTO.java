package com.thiagoti.easypay.external.notification.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class NotificationStatusDTO {

    private boolean message;

    public boolean wasSent() {
        return message;
    }

    public boolean wasNotSent() {
        return Boolean.FALSE.equals(wasSent());
    }
}
