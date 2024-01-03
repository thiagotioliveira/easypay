package com.thiagoti.easypay.external;

import com.thiagoti.easypay.external.model.NotificationStatusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "emailNotificationClient", url = "${app.external.notification.email.url}")
public interface EmailNotificationClient {

    @GetMapping
    NotificationStatusDTO get();
}
