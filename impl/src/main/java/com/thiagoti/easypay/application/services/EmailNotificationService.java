package com.thiagoti.easypay.application.services;

import com.thiagoti.easypay.domain.dto.NotificationDTO;
import com.thiagoti.easypay.domain.exceptions.BusinessRuleException;
import com.thiagoti.easypay.domain.services.NotificationService;
import com.thiagoti.easypay.external.notification.EmailNotificationClient;
import com.thiagoti.easypay.external.notification.dto.NotificationStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class EmailNotificationService implements NotificationService {

    private final EmailNotificationClient client;

    @Override
    public void notify(NotificationDTO notificationDTO) {
        NotificationStatusDTO statusDTO = client.get();
        if (statusDTO.wasNotSent()) {
            throw new BusinessRuleException("unable to send notification.");
        }
        log.debug("e-mail sent. Messsage: {}", notificationDTO);
    }
}
