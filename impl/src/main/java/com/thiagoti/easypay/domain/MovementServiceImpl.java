package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.CreateMovementDTO;
import com.thiagoti.easypay.domain.dto.MovementDTO;
import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MovementServiceImpl implements MovementService {

    private final Validator validator;
    private final MovementMapper mapper;
    private final MovementRepository repository;
    private final WalletService walletService;

    @Override
    @Transactional
    public MovementDTO create(CreateMovementDTO dto) {
        Set<ConstraintViolation<CreateMovementDTO>> constraints = validator.validate(dto);

        if (Boolean.FALSE.equals(constraints.isEmpty())) {
            throw new BusinessRuleException("invalid movement.");
        }

        final var movement = mapper.toEntity(dto);

        if (movement.isDebit()) {
            if (movement.getAmount().compareTo(movement.getWallet().getAmount()) > 0) {
                throw new BusinessRuleException(
                        "insufficient funds for wallet '%s'",
                        movement.getWallet().getId());
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
