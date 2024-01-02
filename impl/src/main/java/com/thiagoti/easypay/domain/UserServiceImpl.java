package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.dto.CreateUserDTO;
import com.thiagoti.easypay.domain.dto.CreateWalletDTO;
import com.thiagoti.easypay.domain.dto.UserDTO;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final WalletService walletService;
    private final UserMapper mapper;
    private final UserRepository repository;

    @Override
    public Optional<UserDTO> getById(UUID id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    @Override
    @Transactional
    public UserDTO create(@Valid CreateUserDTO createUserDTO) {
        final var user = mapper.toDTO(repository.save(mapper.toEntity(createUserDTO)));
        walletService.create(CreateWalletDTO.builder()
                .amount(createUserDTO.getAmount())
                .user(user)
                .build());
        return user;
    }
}
