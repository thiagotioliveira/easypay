package com.thiagoti.easypay.domain.notification;

import com.thiagoti.easypay.domain.dto.NotificationDTO;

public interface NotificationFacade {

    void notify(NotificationDTO notificationDTO);
}
