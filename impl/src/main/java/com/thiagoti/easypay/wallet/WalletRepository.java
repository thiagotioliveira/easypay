package com.thiagoti.easypay.wallet;

import com.thiagoti.easypay.model.entity.Wallet;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, UUID> {

    Optional<Wallet> findByUserId(UUID userId);
}
