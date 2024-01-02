package com.thiagoti.easypay.domain.exception;

public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
