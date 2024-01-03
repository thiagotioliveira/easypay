package com.thiagoti.easypay.transfer;

import com.thiagoti.easypay.model.CreateTransferDTO;
import com.thiagoti.easypay.model.TransferDTO;

public interface TransferService {

    TransferDTO create(CreateTransferDTO createTransferDTO);
}
