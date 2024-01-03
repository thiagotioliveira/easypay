package com.thiagoti.easypay.movement;

import static com.thiagoti.easypay.mock.UserMock.USER_EMAIL;
import static com.thiagoti.easypay.mock.UserMock.USER_NAME;
import static com.thiagoti.easypay.mock.UserMock.createAsUser;
import static com.thiagoti.easypay.mock.WalletMock.createWallet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.thiagoti.easypay.exception.BusinessRuleException;
import com.thiagoti.easypay.model.CreateMovementDTO;
import com.thiagoti.easypay.model.MovementDTO;
import com.thiagoti.easypay.model.entity.Movement.Type;
import com.thiagoti.easypay.model.entity.Wallet;
import com.thiagoti.easypay.user.UserRepository;
import com.thiagoti.easypay.wallet.WalletRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MovementServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private MovementService movementService;

    private Wallet wallet;

    @BeforeEach
    void setUp() throws Exception {
        var user = userRepository.save(createAsUser(USER_NAME, USER_EMAIL));
        wallet = walletRepository.save(createWallet(user, BigDecimal.TEN));
    }

    @Test
    @Transactional
    void shouldMakeMovement() {
        MovementDTO movementDTO = movementService.create(CreateMovementDTO.builder()
                .amount(BigDecimal.ONE)
                .type(Type.CREDIT)
                .walletId(wallet.getId())
                .build());
        assertNotNull(movementDTO);
        assertNotNull(movementDTO.getId());
        assertNotNull(movementDTO.getCreatedAt());
        assertEquals(wallet.getId(), movementDTO.getWalletId());
        assertEquals(Type.CREDIT, movementDTO.getType());
        assertEquals(BigDecimal.ONE, movementDTO.getAmount());
    }

    @Test
    void shouldThrowExceptionBecauseInvalid() {
        assertThrows(BusinessRuleException.class, () -> {
            movementService.create(CreateMovementDTO.builder().build());
        });
    }
}
