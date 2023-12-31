package com.thiagoti.easypay.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.thiagoti.easypay.domain.entity.User;
import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    private static final String USER_CPF_CNPJ = "99999999999";
    private static final String USER_NAME = "Mock da Silva";
    private static final String USER_EMAIL = "mock@mock.test";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

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

        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(createAsUser(USER_CPF_CNPJ, USER_NAME, "mock2@mock.test"));
            em.flush();
        });
    }

    @Test
    void shouldThrowExceptionBecauseEmailMustBeUnique() {
        var user1Saved = userRepository.save(createAsUser(USER_CPF_CNPJ, USER_NAME, USER_EMAIL));
        assertNotNull(user1Saved.getId());
        em.flush();

        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(createAsUser("88888888888", USER_NAME, USER_EMAIL));
            em.flush();
        });
    }

    private static User createAsUser(String cpfCnpj, String name, String email) {
        var user = new User();
        user.setCpfCnpj(cpfCnpj);
        user.setEmail(email);
        user.setName(name);
        user.setPassword("somepass");
        user.setRole(User.Role.USER);
        return user;
    }
}
