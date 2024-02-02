package com.thiagoti.easypay.domain.mappers;

import com.thiagoti.easypay.domain.dto.CreateWalletDTO;
import com.thiagoti.easypay.domain.dto.WalletDTO;
import com.thiagoti.easypay.domain.entities.Wallet;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class WalletMapper {

    protected UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public abstract WalletDTO toDTO(Wallet wallet);

    public abstract Wallet toEntity(WalletDTO dto);

    public abstract Wallet toEntity(CreateWalletDTO dto);
}
