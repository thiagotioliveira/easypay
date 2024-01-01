package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.entity.Transfer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

interface TransferRepository extends CrudRepository<Transfer, UUID> {

    List<Transfer> findAll();
}
