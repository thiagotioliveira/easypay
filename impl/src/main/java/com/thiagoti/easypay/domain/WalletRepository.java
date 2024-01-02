package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.entity.Wallet;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

interface WalletRepository extends CrudRepository<Wallet, UUID> {

    Optional<Wallet> findByUserId(UUID userId);
}
