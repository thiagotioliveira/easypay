package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.WalletDTO;
import com.thiagoti.easypay.domain.entity.Wallet;
import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class WalletServiceImpl implements WalletService {

    private final WalletMapper mapper;
    private final WalletRepository repository;

    @Override
    public Optional<WalletDTO> getById(UUID id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    public WalletDTO update(UUID id, BigDecimal amount) {
        Wallet wallet = repository.findById(id).orElseThrow(() -> new BusinessRuleException("wallet not found."));
        wallet.setAmount(amount);
        return mapper.toDTO(repository.save(wallet));
    }
}
