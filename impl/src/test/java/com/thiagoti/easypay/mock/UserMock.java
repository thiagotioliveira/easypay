package com.thiagoti.easypay.mock;

import com.thiagoti.easypay.domain.entities.User;
import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMock {

    public static final String USER_CPF_CNPJ = "99999999999";
    public static final String USER_NAME = "Mock da Silva";
    public static final String USER_EMAIL = "mock@mock.test";

    public static User createAsUser(String cpfCnpj, String name, String email) {
        var user = createAsUser(name, email);
        user.setCpfCnpj(cpfCnpj);
        return user;
    }

    public static User createAsUser(String name, String email) {
        var user = new User();
        user.setCpfCnpj(generateRandomNumericString(11));
        user.setEmail(email);
        user.setName(name);
        user.setPassword(generateRandomNumericString(11));
        user.setRole(User.Role.USER);
        return user;
    }

    public static User createAsMerchant(String name, String email) {
        var user = new User();
        user.setCpfCnpj(generateRandomNumericString(14));
        user.setEmail(email);
        user.setName(name);
        user.setPassword(generateRandomNumericString(14));
        user.setRole(User.Role.MERCHANT);
        return user;
    }

    public static String generateRandomNumericString(int length) {
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            randomString.append(digit);
        }
        return randomString.toString();
    }
}
