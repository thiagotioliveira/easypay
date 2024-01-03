package com.thiagoti.easypay.wallet;

import com.thiagoti.easypay.model.CreateWalletDTO;
import com.thiagoti.easypay.model.WalletDTO;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    Optional<WalletDTO> getById(UUID id);

    WalletDTO update(UUID id, BigDecimal amount);

    Optional<WalletDTO> getByUser(UUID userId);

    WalletDTO create(CreateWalletDTO createWalletDTO);
}
