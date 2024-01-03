package com.thiagoti.easypay.event;

import com.thiagoti.easypay.model.TransferDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class TransferCreatedEvent {

    private final TransferDTO payload;
}
