package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.CreateTransferDTO;
import com.thiagoti.easypay.domain.dto.TransferDTO;
import com.thiagoti.easypay.domain.entity.Transfer;
import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import java.util.UUID;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
abstract class TransferMapper {
    private MovementService movementService;
    private MovementMapper movementMapper;

    @Autowired
    public void setMovementService(MovementService movementService) {
        this.movementService = movementService;
    }

    @Autowired
    public void setMovementMapper(MovementMapper movementMapper) {
        this.movementMapper = movementMapper;
    }

    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amount", expression = "java(dto.getAmount())")
    abstract Transfer toEntity(CreateTransferDTO dto, UUID movementDebitId, UUID movementCreditId);

    @Mapping(target = "movementDebitId", source = "movementDebit.id")
    @Mapping(target = "movementCreditId", source = "movementCredit.id")
    abstract TransferDTO toDTO(Transfer entity);

    @BeforeMapping
    protected void beforeToEntity(@MappingTarget Transfer transfer, UUID movementDebitId, UUID movementCreditId) {
        transfer.setMovementDebit(movementMapper.toEntity(movementService
                .getById(movementDebitId)
                .orElseThrow(() -> new BusinessRuleException("movement '%s' not found.", movementDebitId))));
        transfer.setMovementCredit(movementMapper.toEntity(movementService
                .getById(movementCreditId)
                .orElseThrow(() -> new BusinessRuleException("movement '%s' not found.", movementCreditId))));
    }
}
