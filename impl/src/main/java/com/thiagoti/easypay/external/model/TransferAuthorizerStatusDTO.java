package com.thiagoti.easypay.external.model;

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
public class TransferAuthorizerStatusDTO {

    public static final String MESSAGE_AUTHORIZED = "Autorizado";

    private String message;

    public boolean isAuthorized() {
        return MESSAGE_AUTHORIZED.equals(message);
    }

    public boolean isInvalid() {
        return Boolean.FALSE.equals(isAuthorized());
    }
}
