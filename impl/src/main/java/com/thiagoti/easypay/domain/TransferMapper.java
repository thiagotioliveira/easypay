package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.CreateTransferDTO;
import com.thiagoti.easypay.domain.dto.TransferDTO;
import com.thiagoti.easypay.domain.entity.Transfer;
import com.thiagoti.easypay.domain.entity.Wallet;
import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
abstract class TransferMapper {

    private WalletService wallerService;
    private WalletMapper walletMapper;

    @Autowired
    public void setWalletMapper(WalletMapper walletMapper) {
        this.walletMapper = walletMapper;
    }

    @Autowired
    public void setWallerService(WalletService wallerService) {
        this.wallerService = wallerService;
    }

    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    abstract Transfer toEntity(CreateTransferDTO dto);

    @Mapping(target = "walletFromId", source = "walletFrom.id")
    @Mapping(target = "walletToId", source = "walletTo.id")
    abstract TransferDTO toDTO(Transfer entity);

    @BeforeMapping
    protected void beforeToEntity(@MappingTarget Transfer transfer, CreateTransferDTO dto) {
        Wallet walletFrom = wallerService
                .getById(dto.getWalletFromId())
                .map(walletMapper::toEntity)
                .orElseThrow(() ->
                        new BusinessRuleException(String.format("wallet '%s' not found.", dto.getWalletFromId())));
        Wallet walletTo = wallerService
                .getById(dto.getWalletToId())
                .map(walletMapper::toEntity)
                .orElseThrow(
                        () -> new BusinessRuleException(String.format("wallet '%s' not found.", dto.getWalletToId())));
        transfer.setWalletFrom(walletFrom);
        transfer.setWalletTo(walletTo);
    }
}
