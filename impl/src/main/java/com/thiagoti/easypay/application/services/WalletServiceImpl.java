package com.thiagoti.easypay.application.services;

import com.thiagoti.easypay.domain.dto.CreateWalletDTO;
import com.thiagoti.easypay.domain.dto.WalletDTO;
import com.thiagoti.easypay.domain.entities.Wallet;
import com.thiagoti.easypay.domain.exceptions.BusinessRuleException;
import com.thiagoti.easypay.domain.mappers.WalletMapper;
import com.thiagoti.easypay.domain.repositories.WalletRepository;
import com.thiagoti.easypay.domain.services.WalletService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletDTO> getByUser(UUID userId) {
        return repository.findByUserId(userId).map(mapper::toDTO);
    }

    @Override
    public WalletDTO create(@Valid CreateWalletDTO createWalletDTO) {
        return mapper.toDTO(repository.save(mapper.toEntity(createWalletDTO)));
    }
}
