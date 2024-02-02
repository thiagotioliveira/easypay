package com.thiagoti.easypay.application.services;

import com.thiagoti.easypay.domain.dto.NotificationDTO;
import com.thiagoti.easypay.domain.services.NotificationFacade;
import com.thiagoti.easypay.domain.services.NotificationService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class NotificationFacadeImpl implements NotificationFacade {

    private final Set<NotificationService> services;

    @Override
    public void notify(NotificationDTO notificationDTO) {
        for (NotificationService notificationService : services) {
            try {
                notificationService.notify(notificationDTO);
            } catch (Exception e) {
                log.error("error when trying to send the notification.", e);
            }
        }
    }
}
