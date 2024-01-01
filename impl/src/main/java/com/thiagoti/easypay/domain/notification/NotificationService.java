package com.thiagoti.easypay.domain.notification;

import com.thiagoti.easypay.domain.dto.NotificationDTO;

interface NotificationService {

    void notify(NotificationDTO notificationDTO);
}
