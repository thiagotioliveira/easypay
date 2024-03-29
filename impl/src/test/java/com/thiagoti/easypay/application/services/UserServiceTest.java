package com.thiagoti.easypay.application.services;

import static com.thiagoti.easypay.mock.UserMock.USER_EMAIL;
import static com.thiagoti.easypay.mock.UserMock.USER_NAME;
import static com.thiagoti.easypay.mock.UserMock.createAsUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.thiagoti.easypay.domain.dto.UserDTO;
import com.thiagoti.easypay.domain.mappers.UserMapper;
import com.thiagoti.easypay.domain.mappers.UserMapperImpl;
import com.thiagoti.easypay.domain.repositories.UserRepository;
import com.thiagoti.easypay.domain.services.UserService;
import com.thiagoti.easypay.domain.services.WalletService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletService walletService;

    private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    void setUp() throws Exception {
        this.userMapper = new UserMapperImpl();
        this.userService = new UserServiceImpl(walletService, userMapper, userRepository);
    }

    @Test
    void shouldFindById() {
        var userSaved = createAsUser(USER_NAME, USER_EMAIL);
        userSaved.setId(UUID.randomUUID());
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(userSaved));
        Optional<UserDTO> userOptional = this.userService.getById(UUID.randomUUID());
        assertTrue(userOptional.isPresent());

        var user = userOptional.get();

        assertNotNull(user.getId());
        assertEquals(userSaved.getName(), user.getName());
        assertEquals(userSaved.getCpfCnpj(), user.getCpfCnpj());
        assertEquals(userSaved.getEmail(), user.getEmail());
        assertEquals(userSaved.getRole().name(), user.getRole().name());
    }
}
