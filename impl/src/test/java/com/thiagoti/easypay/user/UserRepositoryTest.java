package com.thiagoti.easypay.user;

import static com.thiagoti.easypay.mock.UserMock.USER_CPF_CNPJ;
import static com.thiagoti.easypay.mock.UserMock.USER_EMAIL;
import static com.thiagoti.easypay.mock.UserMock.USER_NAME;
import static com.thiagoti.easypay.mock.UserMock.createAsUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    void shouldListUsers() {
        userRepository.save(createAsUser(USER_CPF_CNPJ, USER_NAME, USER_EMAIL));
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void shouldSaveUser() {
        var userSaved = userRepository.save(createAsUser(USER_CPF_CNPJ, USER_NAME, USER_EMAIL));
        assertNotNull(userSaved.getId());
    }

    @Test
    void shouldThrowExceptionBecauseCpfCnpjMustBeUnique() {
        var user1Saved = userRepository.save(createAsUser(USER_CPF_CNPJ, USER_NAME, "mock1@mock.test"));
        assertNotNull(user1Saved.getId());
        em.flush();

        assertThrows(PersistenceException.class, () -> {
            userRepository.save(createAsUser(USER_CPF_CNPJ, USER_NAME, "mock2@mock.test"));
            em.flush();
        });
    }

    @Test
    void shouldThrowExceptionBecauseEmailMustBeUnique() {
        var user1Saved = userRepository.save(createAsUser(USER_CPF_CNPJ, USER_NAME, USER_EMAIL));
        assertNotNull(user1Saved.getId());
        em.flush();

        assertThrows(PersistenceException.class, () -> {
            userRepository.save(createAsUser("88888888888", USER_NAME, USER_EMAIL));
            em.flush();
        });
    }
}
