package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.UserDTO;
import com.thiagoti.easypay.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper
interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO dto);
}
