package com.thiagoti.easypay.transfer;

import com.thiagoti.easypay.annotation.PublishResult;
import com.thiagoti.easypay.exception.BusinessRuleException;
import com.thiagoti.easypay.model.CreateMovementDTO;
import com.thiagoti.easypay.model.CreateTransferDTO;
import com.thiagoti.easypay.model.MovementDTO;
import com.thiagoti.easypay.model.TransferDTO;
import com.thiagoti.easypay.model.entity.Movement.Type;
import com.thiagoti.easypay.movement.MovementService;
import com.thiagoti.easypay.wallet.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class TransferServiceImpl implements TransferService {

    private final TransferAuthorizerService transferAuthorizerService;
    private final TransferMapper mapper;
    private final TransferRepository repository;
    private final MovementService movementService;
    private final WalletService walletService;

    @Override
    @Transactional
    @PublishResult
    public TransferDTO create(@Valid CreateTransferDTO createTransferDTO) {
        transferAuthorizerService.authorize();

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
