package com.thiagoti.easypay.notification;

import com.thiagoti.easypay.model.NotificationDTO;

interface NotificationService {

    void notify(NotificationDTO notificationDTO);
}
