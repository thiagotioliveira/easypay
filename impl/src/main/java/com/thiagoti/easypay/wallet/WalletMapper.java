package com.thiagoti.easypay.wallet;

import com.thiagoti.easypay.model.CreateWalletDTO;
import com.thiagoti.easypay.model.WalletDTO;
import com.thiagoti.easypay.model.entity.Wallet;
import com.thiagoti.easypay.user.UserMapper;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class WalletMapper {

    protected UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    abstract WalletDTO toDTO(Wallet wallet);

    public abstract Wallet toEntity(WalletDTO dto);

    abstract Wallet toEntity(CreateWalletDTO dto);
}
