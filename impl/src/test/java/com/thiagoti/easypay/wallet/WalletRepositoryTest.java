package com.thiagoti.easypay.wallet;

import static com.thiagoti.easypay.mock.UserMock.USER_CPF_CNPJ;
import static com.thiagoti.easypay.mock.UserMock.USER_EMAIL;
import static com.thiagoti.easypay.mock.UserMock.USER_NAME;
import static com.thiagoti.easypay.mock.UserMock.createAsUser;
import static com.thiagoti.easypay.mock.WalletMock.createWallet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.thiagoti.easypay.model.entity.User;
import com.thiagoti.easypay.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class WalletRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private EntityManager em;

    private User user;

    @BeforeEach
    void setUp() throws Exception {
        user = userRepository.save(createAsUser(USER_CPF_CNPJ, USER_NAME, USER_EMAIL));
    }

    @Test
    void shouldSave() {
        var wallet = walletRepository.save(createWallet(user, BigDecimal.TEN));
        assertNotNull(wallet.getId());
    }

    @Test
    void shouldUpdate() {
        var wallet = walletRepository.save(createWallet(user, BigDecimal.TEN));
        em.flush();
        assertEquals(0, wallet.getVersion());

        wallet = walletRepository.findById(wallet.getId()).orElseThrow();
        wallet.setAmount(BigDecimal.ONE);
        wallet = walletRepository.save(wallet);
        em.flush();
        assertEquals(1, wallet.getVersion());
    }

    @Test
    void shouldThrowExceptionBecauseUserMustBeUnique() {
        var wallet1 = createWallet(user, BigDecimal.TEN);
        var wallet1Saved = walletRepository.save(wallet1);
        em.flush();
        assertNotNull(wallet1Saved.getId());

        var wallet2 = createWallet(user, BigDecimal.TEN);
        assertThrows(PersistenceException.class, () -> {
            walletRepository.save(wallet2);
            em.flush();
        });
    }

    @Test
    void shouldThrowExceptionBecauseAmountIsNegative() {
        var wallet = createWallet(user, new BigDecimal("-10"));
        assertThrows(ConstraintViolationException.class, () -> {
            walletRepository.save(wallet);
            em.flush();
        });
    }
}
