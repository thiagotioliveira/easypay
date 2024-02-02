package com.thiagoti.easypay.domain.services;

import com.thiagoti.easypay.domain.dto.CreateWalletDTO;
import com.thiagoti.easypay.domain.dto.WalletDTO;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    Optional<WalletDTO> getById(UUID id);

    WalletDTO update(UUID id, BigDecimal amount);

    Optional<WalletDTO> getByUser(UUID userId);

    WalletDTO create(CreateWalletDTO createWalletDTO);
}
