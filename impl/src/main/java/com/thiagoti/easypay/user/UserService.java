package com.thiagoti.easypay.user;

import com.thiagoti.easypay.model.CreateUserDTO;
import com.thiagoti.easypay.model.UserDTO;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<UserDTO> getById(UUID id);

    UserDTO create(CreateUserDTO createUserDTO);
}
