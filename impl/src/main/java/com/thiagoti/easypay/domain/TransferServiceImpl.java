package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.annotation.SendNotification;
import com.thiagoti.easypay.domain.dto.CreateTransferDTO;
import com.thiagoti.easypay.domain.dto.TransferDTO;
import com.thiagoti.easypay.domain.entity.Transfer;
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
    private final WalletService walletService;

    @Override
    @Transactional
    @SendNotification
    public TransferDTO create(CreateTransferDTO createTransferDTO) {
        transferAuthorizerService.authorize();

        final var transfer = mapper.toEntity(createTransferDTO);
        if (transfer.getAmount().compareTo(transfer.getWalletFrom().getAmount()) > 0) {
            throw new BusinessRuleException("insufficient funds.");
        }

        Set<ConstraintViolation<Transfer>> constraints = validator.validate(transfer);

        if (Boolean.FALSE.equals(constraints.isEmpty())) {
            throw new BusinessRuleException("invalid transfer.");
        }

        walletService.update(
                transfer.getWalletFrom().getId(),
                transfer.getWalletFrom().getAmount().subtract(transfer.getAmount()));
        walletService.update(
                transfer.getWalletTo().getId(),
                transfer.getWalletTo().getAmount().add(transfer.getAmount()));
        return mapper.toDTO(repository.save(transfer));
    }
}
