package com.thiagoti.easypay.notification;

import com.thiagoti.easypay.exception.BusinessRuleException;
import com.thiagoti.easypay.external.EmailNotificationClient;
import com.thiagoti.easypay.external.model.NotificationStatusDTO;
import com.thiagoti.easypay.model.NotificationDTO;
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
