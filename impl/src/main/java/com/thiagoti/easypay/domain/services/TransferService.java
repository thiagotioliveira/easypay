package com.thiagoti.easypay.domain.services;

import com.thiagoti.easypay.domain.dto.CreateTransferDTO;
import com.thiagoti.easypay.domain.dto.TransferDTO;

public interface TransferService {

    TransferDTO create(CreateTransferDTO createTransferDTO);
}
