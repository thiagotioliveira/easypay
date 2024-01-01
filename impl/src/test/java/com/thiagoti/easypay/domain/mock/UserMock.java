package com.thiagoti.easypay.domain.mock;

import java.util.Random;

import com.thiagoti.easypay.domain.entity.User;

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
    	user.setCpfCnpj(generateRandomNumericString());
    	user.setEmail(email);
    	user.setName(name);
    	user.setPassword(generateRandomNumericString());
    	user.setRole(User.Role.USER);
    	return user;
    }
    
    public static String generateRandomNumericString() {
        Random random = new Random();
        int length = random.nextBoolean() ? 11 : 14;

        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            randomString.append(digit);
        }
        return randomString.toString();
    }
}
