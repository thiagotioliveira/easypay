package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMock {

    public static final String USER_CPF_CNPJ = "99999999999";
    public static final String USER_NAME = "Mock da Silva";
    public static final String USER_EMAIL = "mock@mock.test";

    public static User createAsUser(String cpfCnpj, String name, String email) {
        var user = new User();
        user.setCpfCnpj(cpfCnpj);
        user.setEmail(email);
        user.setName(name);
        user.setPassword("somepass");
        user.setRole(User.Role.USER);
        return user;
    }
}
