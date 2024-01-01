package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.WalletDTO;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    Optional<WalletDTO> getById(UUID id);

    WalletDTO update(UUID id, BigDecimal amount);
}
