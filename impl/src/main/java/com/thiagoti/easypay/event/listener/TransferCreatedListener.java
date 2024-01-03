package com.thiagoti.easypay.event.listener;

import com.thiagoti.easypay.event.TransferCreatedEvent;
import com.thiagoti.easypay.model.NotificationDTO;
import com.thiagoti.easypay.notification.NotificationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransferCreatedListener {

    private final NotificationFacade facade;

    @EventListener(TransferCreatedEvent.class)
    public void onTransferCreatedEvent(TransferCreatedEvent event) {
        log.debug("receive event {}", event);
        facade.notify(NotificationDTO.createTransfer(event.getPayload()));
    }
}
