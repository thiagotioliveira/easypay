package com.thiagoti.easypay.infra.events.listeners;

import com.thiagoti.easypay.domain.dto.NotificationDTO;
import com.thiagoti.easypay.domain.events.TransferCreatedEvent;
import com.thiagoti.easypay.domain.services.NotificationFacade;
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
