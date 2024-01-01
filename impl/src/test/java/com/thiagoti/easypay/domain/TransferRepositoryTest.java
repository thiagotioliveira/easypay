package com.thiagoti.easypay.domain;

import static com.thiagoti.easypay.domain.mock.UserMock.createAsUser;
import static com.thiagoti.easypay.domain.mock.WalletMock.createWallet;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.thiagoti.easypay.domain.entity.Transfer;
import com.thiagoti.easypay.domain.entity.Wallet;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
class TransferRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private TransferRepository transferRepository;
	
	@Autowired
	private EntityManager em;
	
	private Wallet wallet1;
	private Wallet wallet2;
	
	@BeforeEach
	void setUp() throws Exception {
		var user1 = createAsUser("Mock 1", "mock1@mock.test");
		userRepository.save(user1);
		var user2 = createAsUser("Mock 2", "mock2@mock.test");
		userRepository.save(user2);
		
		this.wallet1 = createWallet(user1, BigDecimal.TEN);
		walletRepository.save(wallet1);
		this.wallet2 = createWallet(user2, BigDecimal.TEN);
		walletRepository.save(wallet2);
		
	}

	@Test
	void shouldMakeTransfer() {
		var transfer = new Transfer();
		transfer.setCreatedAt(OffsetDateTime.now());
		transfer.setWalletFrom(wallet1);
		transfer.setWalletTo(wallet2);
		transfer.setAmount(BigDecimal.TEN);
		var transferSaved = transferRepository.save(transfer);
		em.flush();
		assertNotNull(transferSaved.getId());
	}
	
	@Test
	void shouldThrowExceptionBecauseInvalidTransfer() {
		var transfer = new Transfer();
		transfer.setCreatedAt(OffsetDateTime.now());
		transfer.setWalletFrom(wallet1);
		transfer.setWalletTo(wallet1);
		transfer.setAmount(BigDecimal.TEN);
		assertThrows(ConstraintViolationException.class, () -> {
			transferRepository.save(transfer);
			em.flush();
		});
	}

}
