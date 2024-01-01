package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.UserDTO;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<UserDTO> getById(UUID id);
}
