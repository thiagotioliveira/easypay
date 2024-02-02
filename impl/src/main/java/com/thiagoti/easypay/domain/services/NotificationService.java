package com.thiagoti.easypay.domain.services;

import com.thiagoti.easypay.domain.dto.NotificationDTO;

public interface NotificationService {

    void notify(NotificationDTO notificationDTO);
}
