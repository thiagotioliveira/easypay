package com.thiagoti.easypay.domain.exceptions;

import jakarta.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BusinessRuleException extends RuntimeException {

    @Getter
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    @Getter
    private Set<ConstraintViolation<Object>> constraints = new HashSet<>();

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, Object... objects) {
        super(String.format(message, objects));
    }

    public BusinessRuleException(HttpStatus status, String message, Object... objects) {
        super(String.format(message, objects));
        this.status = status;
    }

    public BusinessRuleException(String message, Set<ConstraintViolation<Object>> constraints) {
        super(message);
        this.constraints.addAll(constraints);
    }
}
