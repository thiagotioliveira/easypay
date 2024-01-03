package com.thiagoti.easypay.user;

import com.thiagoti.easypay.model.CreateUserDTO;
import com.thiagoti.easypay.model.UserDTO;
import com.thiagoti.easypay.model.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO dto);

    User toEntity(CreateUserDTO dto);
}
