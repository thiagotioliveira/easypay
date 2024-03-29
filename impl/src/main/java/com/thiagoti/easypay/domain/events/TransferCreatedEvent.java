package com.thiagoti.easypay.domain.events;

import com.thiagoti.easypay.domain.dto.TransferDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class TransferCreatedEvent {

    private final TransferDTO payload;
}
