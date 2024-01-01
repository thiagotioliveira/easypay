package com.thiagoti.easypay.domain.mock;

import java.math.BigDecimal;

import com.thiagoti.easypay.domain.entity.User;
import com.thiagoti.easypay.domain.entity.Wallet;

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
