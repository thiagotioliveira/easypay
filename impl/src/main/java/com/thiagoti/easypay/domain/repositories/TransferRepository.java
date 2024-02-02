package com.thiagoti.easypay.domain.repositories;

import com.thiagoti.easypay.domain.entities.Transfer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface TransferRepository extends CrudRepository<Transfer, UUID> {

    List<Transfer> findAll();
}
