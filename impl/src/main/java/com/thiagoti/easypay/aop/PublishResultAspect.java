package com.thiagoti.easypay.aop;

import com.thiagoti.easypay.event.TransferCreatedEvent;
import com.thiagoti.easypay.model.TransferDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class PublishResultAspect {

    private final ApplicationEventPublisher applicationEventPublisher;

    @AfterReturning(value = "@annotation(com.thiagoti.easypay.annotation.PublishResult)", returning = "result")
    public void sendNotification(JoinPoint joinPoint, Object result) {
        log.debug("publish event for {}", result);
        applicationEventPublisher.publishEvent(new TransferCreatedEvent((TransferDTO) result));
    }
}
