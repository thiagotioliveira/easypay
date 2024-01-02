package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.CreateWalletDTO;
import com.thiagoti.easypay.domain.dto.WalletDTO;
import com.thiagoti.easypay.domain.entity.Wallet;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
abstract class WalletMapper {

    protected UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    abstract WalletDTO toDTO(Wallet wallet);

    abstract Wallet toEntity(WalletDTO dto);

    abstract Wallet toEntity(CreateWalletDTO dto);
}
