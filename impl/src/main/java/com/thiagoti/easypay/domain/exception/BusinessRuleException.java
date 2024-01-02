package com.thiagoti.easypay.domain.exception;

import jakarta.validation.ConstraintViolation;
import java.util.Set;
import lombok.Getter;

public class BusinessRuleException extends RuntimeException {

    @Getter
    private Set<ConstraintViolation<Object>> constraints;

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, Object... objects) {
        super(String.format(message, objects));
    }

    public BusinessRuleException(String message, Set<ConstraintViolation<Object>> constraints) {
        this.constraints = constraints;
    }
}
