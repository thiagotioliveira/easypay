package com.thiagoti.easypay.domain.mappers;

import com.thiagoti.easypay.domain.dto.CreateMovementDTO;
import com.thiagoti.easypay.domain.dto.MovementDTO;
import com.thiagoti.easypay.domain.entities.Movement;
import com.thiagoti.easypay.domain.exceptions.BusinessRuleException;
import com.thiagoti.easypay.domain.services.WalletService;
import java.util.UUID;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class MovementMapper {

    private WalletService walletService;
    private WalletMapper walletMapper;

    @Autowired
    public void setWalletService(WalletService walletService) {
        this.walletService = walletService;
    }

    @Autowired
    public void setWalletMapper(WalletMapper walletMapper) {
        this.walletMapper = walletMapper;
    }

    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    public abstract Movement toEntity(CreateMovementDTO dto);

    public abstract Movement toEntity(MovementDTO dto);

    @Mapping(target = "walletId", source = "wallet.id")
    public abstract MovementDTO toDTO(Movement entity);

    @BeforeMapping
    void beforeToEntity(@MappingTarget Movement movement, CreateMovementDTO dto) {
        beforeToEntity(movement, dto.getWalletId());
    }

    @BeforeMapping
    void beforeToEntity(@MappingTarget Movement movement, MovementDTO dto) {
        beforeToEntity(movement, dto.getWalletId());
    }

    void beforeToEntity(Movement movement, UUID walletId) {
        movement.setWallet(walletMapper.toEntity(
                walletService.getById(walletId).orElseThrow(() -> new BusinessRuleException("wallet not found."))));
    }
}
