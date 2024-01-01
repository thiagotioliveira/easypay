package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.CreateTransferDTO;
import com.thiagoti.easypay.domain.dto.TransferDTO;

public interface TransferService {

    TransferDTO create(CreateTransferDTO createTransferDTO);
}
