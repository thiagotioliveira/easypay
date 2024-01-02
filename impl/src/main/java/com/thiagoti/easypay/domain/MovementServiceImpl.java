package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.CreateMovementDTO;
import com.thiagoti.easypay.domain.dto.MovementDTO;
import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MovementServiceImpl implements MovementService {

    private final MovementMapper mapper;
    private final MovementRepository repository;
    private final WalletService walletService;

    @Override
    @Transactional
    public MovementDTO create(@Valid CreateMovementDTO dto) {
        final var movement = mapper.toEntity(dto);

        if (movement.isDebit()) {
            if (movement.getAmount().compareTo(movement.getWallet().getAmount()) > 0) {
                throw new BusinessRuleException(
                        "insufficient funds for user '%s'",
                        movement.getWallet().getUser().getId());
            }
            walletService.update(
                    movement.getWallet().getId(),
                    movement.getWallet().getAmount().subtract(movement.getAmount()));
        } else {
            walletService.update(
                    movement.getWallet().getId(),
                    movement.getWallet().getAmount().add(movement.getAmount()));
        }

        return mapper.toDTO(repository.save(movement));
    }

    @Override
    public Optional<MovementDTO> getById(UUID id) {
        return repository.findById(id).map(mapper::toDTO);
    }
}
