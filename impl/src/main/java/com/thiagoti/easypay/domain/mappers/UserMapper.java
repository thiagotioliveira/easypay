package com.thiagoti.easypay.domain.mappers;

import com.thiagoti.easypay.domain.dto.CreateUserDTO;
import com.thiagoti.easypay.domain.dto.UserDTO;
import com.thiagoti.easypay.domain.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO dto);

    User toEntity(CreateUserDTO dto);
}
