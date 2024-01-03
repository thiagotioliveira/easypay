package com.thiagoti.easypay.wallet;

import static com.thiagoti.easypay.mock.UserMock.USER_EMAIL;
import static com.thiagoti.easypay.mock.UserMock.USER_NAME;
import static com.thiagoti.easypay.mock.UserMock.createAsUser;
import static com.thiagoti.easypay.mock.WalletMock.createWallet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thiagoti.easypay.model.WalletDTO;
import com.thiagoti.easypay.user.UserMapperImpl;
import com.thiagoti.easypay.user.UserRepository;
import com.thiagoti.easypay.user.UserServiceImpl;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import({WalletServiceImpl.class, WalletMapperImpl.class, UserServiceImpl.class, UserMapperImpl.class})
@ActiveProfiles("test")
class WalletServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;

    @BeforeEach
    void setUp() throws Exception {}

    @Test
    void shouldFindById() {
        var user = userRepository.save(createAsUser(USER_NAME, USER_EMAIL));
        var wallet = walletRepository.save(createWallet(user, BigDecimal.TEN));

        Optional<WalletDTO> walletOptional = walletService.getById(wallet.getId());
        assertTrue(walletOptional.isPresent());

        WalletDTO walletDTO = walletOptional.get();
        assertNotNull(walletDTO.getId());
        assertEquals(wallet.getUser().getId(), walletDTO.getUser().getId());
        assertEquals(wallet.getAmount(), walletDTO.getAmount());
    }

    @Test
    void shouldNotFindById() {
        Optional<WalletDTO> wallet = walletService.getById(UUID.randomUUID());
        assertTrue(wallet.isEmpty());
    }

    @Test
    void shouldUpdate() {
        var user = userRepository.save(createAsUser(USER_NAME, USER_EMAIL));
        var wallet = walletRepository.save(createWallet(user, BigDecimal.TEN));

        var walletDTO = walletService.update(wallet.getId(), BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, walletDTO.getAmount());

        walletDTO = walletService.getById(wallet.getId()).orElseThrow();
        assertEquals(BigDecimal.ONE, walletDTO.getAmount());
    }
}
