package com.thiagoti.easypay.domain.services;

import com.thiagoti.easypay.domain.dto.NotificationDTO;

public interface NotificationFacade {

    void notify(NotificationDTO notificationDTO);
}
