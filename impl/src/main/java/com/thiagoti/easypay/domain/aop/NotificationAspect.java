package com.thiagoti.easypay.domain.aop;

import com.thiagoti.easypay.domain.dto.NotificationDTO;
import com.thiagoti.easypay.domain.notification.NotificationFacade;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class NotificationAspect {

    private final NotificationFacade facade;

    @AfterReturning(
            value = "@annotation(com.thiagoti.easypay.domain.annotation.SendNotification)",
            returning = "result")
    public void sendNotification(JoinPoint joinPoint, Object result) {
        facade.notify(NotificationDTO.createTransfer(result));
    }
}
