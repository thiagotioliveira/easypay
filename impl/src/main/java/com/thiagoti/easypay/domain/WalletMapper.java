package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.WalletDTO;
import com.thiagoti.easypay.domain.entity.Wallet;
import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
abstract class WalletMapper {

    protected UserService userService;
    protected UserMapper userMapper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Mapping(target = "userId", source = "user.id")
    abstract WalletDTO toDTO(Wallet wallet);

    abstract Wallet toEntity(WalletDTO dto);

    @BeforeMapping
    protected void beforeToEntity(@MappingTarget Wallet wallet, WalletDTO dto) {
        wallet.setUser(userService
                .getById(dto.getUserId())
                .map(userMapper::toEntity)
                .orElseThrow(() -> new BusinessRuleException("user not found.")));
    }
}
