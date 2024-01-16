package com.thiagoti.easypay.web;

import com.thiagoti.easypay.api.model.Error;
import com.thiagoti.easypay.api.model.ErrorDetail;
import com.thiagoti.easypay.exception.BusinessRuleException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UrlPathHelper;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Error> handleBusinessRuleException(BusinessRuleException ex, HttpServletRequest request) {
        var error = new Error();
        error.setMessage(ex.getMessage());
        error.setStatus(400);
        error.setTimestamp(OffsetDateTime.now());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setPath(getPathFromRequest(request));
        if (Boolean.FALSE.equals(ex.getConstraints().isEmpty())) {
            ex.getConstraints().forEach(c -> {
                var detail = new ErrorDetail();
                detail.setField(c.getPropertyPath().toString());
                detail.setMessages(List.of(c.getMessage()));
                error.addDetailsItem(detail);
            });
        }
        return new ResponseEntity<Error>(error, ex.getStatus());
    }

    private static String getPathFromRequest(HttpServletRequest request) {
        var path = new UrlPathHelper().getPathWithinApplication(request);
        if (Strings.isNotBlank(request.getQueryString())) {
            path += "?" + request.getQueryString();
        }
        return path;
    }
}
