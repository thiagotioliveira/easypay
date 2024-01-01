package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.entity.Transfer;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

interface TransferRepository extends CrudRepository<Transfer, UUID> {}
