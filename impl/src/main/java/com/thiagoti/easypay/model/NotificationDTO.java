package com.thiagoti.easypay.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class NotificationDTO {

    private Type type;

    private String message;

    private Object payload;

    public static NotificationDTO createTransfer(Object payload) {
        return new NotificationDTO(Type.TRANSFER, "transfer has been processed.", payload);
    }

    @RequiredArgsConstructor
    @Getter
    public enum Type {
        TRANSFER(TransferDTO.class);

        private final Class payloadClass;
    }
}
