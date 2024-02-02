package com.thiagoti.easypay.mock;

import com.thiagoti.easypay.domain.entities.User;
import com.thiagoti.easypay.domain.entities.Wallet;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletMock {

    public static Wallet createWallet(User user, BigDecimal amount) {
        var wallet = new Wallet();
        wallet.setUser(user);
        wallet.setAmount(amount);
        return wallet;
    }
}
