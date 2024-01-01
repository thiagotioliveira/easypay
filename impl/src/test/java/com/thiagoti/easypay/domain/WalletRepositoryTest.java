package com.thiagoti.easypay.domain;

import static com.thiagoti.easypay.domain.UserMock.USER_CPF_CNPJ;
import static com.thiagoti.easypay.domain.UserMock.USER_EMAIL;
import static com.thiagoti.easypay.domain.UserMock.USER_NAME;
import static com.thiagoti.easypay.domain.UserMock.createAsUser;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.thiagoti.easypay.domain.entity.User;
import com.thiagoti.easypay.domain.entity.Wallet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
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
        var wallet = createWallet(BigDecimal.TEN);
        var wallet1Saved = walletRepository.save(wallet);
        assertNotNull(wallet1Saved.getId());
    }

    @Test
    void shouldThrowExceptionBecauseUserMustBeUnique() {
        var wallet1 = createWallet(BigDecimal.TEN);
        var wallet1Saved = walletRepository.save(wallet1);
        em.flush();
        assertNotNull(wallet1Saved.getId());

        var wallet2 = createWallet(BigDecimal.TEN);
        assertThrows(PersistenceException.class, () -> {
            walletRepository.save(wallet2);
            em.flush();
        });
    }

    @Test
    void shouldThrowExceptionBecauseAmountIsNegative() {
        var wallet = createWallet(new BigDecimal("-10"));
        assertThrows(ConstraintViolationException.class, () -> {
            walletRepository.save(wallet);
            em.flush();
        });
    }

    private Wallet createWallet(BigDecimal amount) {
        var wallet = new Wallet();
        wallet.setUser(user);
        wallet.setAmount(amount);
        return wallet;
    }
}
