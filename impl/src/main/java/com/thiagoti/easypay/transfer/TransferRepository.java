package com.thiagoti.easypay.transfer;

import com.thiagoti.easypay.model.entity.Transfer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

interface TransferRepository extends CrudRepository<Transfer, UUID> {

    List<Transfer> findAll();
}
