package com.thiagoti.easypay.domain;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.thiagoti.easypay.domain.entity.Transfer;

interface TransferRepository extends CrudRepository<Transfer, UUID> {

}
