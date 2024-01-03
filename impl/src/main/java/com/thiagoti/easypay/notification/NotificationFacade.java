package com.thiagoti.easypay.notification;

import com.thiagoti.easypay.model.NotificationDTO;

public interface NotificationFacade {

    void notify(NotificationDTO notificationDTO);
}
