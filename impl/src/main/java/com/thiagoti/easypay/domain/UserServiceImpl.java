package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.UserDTO;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    @Override
    public Optional<UserDTO> getById(UUID id) {
        return repository.findById(id).map(mapper::toDTO);
    }
}
