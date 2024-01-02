package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.annotation.SendNotification;
import com.thiagoti.easypay.domain.dto.CreateMovementDTO;
import com.thiagoti.easypay.domain.dto.CreateTransferDTO;
import com.thiagoti.easypay.domain.dto.MovementDTO;
import com.thiagoti.easypay.domain.dto.TransferDTO;
import com.thiagoti.easypay.domain.entity.Movement.Type;
import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class TransferServiceImpl implements TransferService {

    private final Validator validator;
    private final TransferAuthorizerService transferAuthorizerService;
    private final TransferMapper mapper;
    private final TransferRepository repository;
    private final MovementService movementService;
    private final WalletService walletService;

    @Override
    @Transactional
    @SendNotification
    public TransferDTO create(CreateTransferDTO createTransferDTO) {
        transferAuthorizerService.authorize();

        Set<ConstraintViolation<CreateTransferDTO>> constraints = validator.validate(createTransferDTO);

        if (Boolean.FALSE.equals(constraints.isEmpty())) {
            throw new BusinessRuleException("invalid transfer.");
        }

        MovementDTO movementDebitDTO = movementService.create(CreateMovementDTO.builder()
                .amount(createTransferDTO.getAmount())
                .type(Type.DEBIT)
                .walletId(walletService
                        .getByUser(createTransferDTO.getUserFrom().getId())
                        .orElseThrow(() -> new BusinessRuleException(
                                "wallet for user '%s' not found.",
                                createTransferDTO.getUserFrom().getId()))
                        .getId())
                .build());

        MovementDTO movementCreditDTO = movementService.create(CreateMovementDTO.builder()
                .amount(createTransferDTO.getAmount())
                .type(Type.CREDIT)
                .walletId(walletService
                        .getByUser(createTransferDTO.getUserTo().getId())
                        .orElseThrow(() -> new BusinessRuleException(
                                "wallet for user '%s' not found.",
                                createTransferDTO.getUserTo().getId()))
                        .getId())
                .build());

        return mapper.toDTO(repository.save(
                mapper.toEntity(createTransferDTO, movementDebitDTO.getId(), movementCreditDTO.getId())));
    }
}
