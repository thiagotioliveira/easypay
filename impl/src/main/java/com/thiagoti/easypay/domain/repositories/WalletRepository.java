package com.thiagoti.easypay.domain.repositories;

import com.thiagoti.easypay.domain.entities.Wallet;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, UUID> {

    @Query("select w from Wallet w inner join w.user u where u.id = :userId")
    Optional<Wallet> findByUserId(UUID userId);
}
