package com.thiagoti.easypay.domain;

import static com.thiagoti.easypay.domain.mock.UserMock.USER_EMAIL;
import static com.thiagoti.easypay.domain.mock.UserMock.USER_NAME;
import static com.thiagoti.easypay.domain.mock.UserMock.createAsShopkeeper;
import static com.thiagoti.easypay.domain.mock.UserMock.createAsUser;
import static com.thiagoti.easypay.domain.mock.WalletMock.createWallet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

import com.thiagoti.easypay.domain.dto.CreateTransferDTO;
import com.thiagoti.easypay.domain.dto.TransferDTO;
import com.thiagoti.easypay.domain.entity.Wallet;
import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@DataJpaTest
@Import({
    TransferServiceImpl.class,
    TransferMapperImpl.class,
    WalletMapperImpl.class,
    UserServiceImpl.class,
    UserMapperImpl.class,
    WalletServiceImpl.class,
    LocalValidatorFactoryBean.class
})
class TransferServiceTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferRepository transferRepository;

    @SpyBean
    private WalletService walletService;

    private Wallet wallet1;
    private Wallet wallet2;

    @BeforeEach
    void setUp() throws Exception {
        walletRepository.deleteAll();
        userRepository.deleteAll();
        var user1 = userRepository.save(createAsUser(USER_NAME + "1", USER_EMAIL + "1"));
        var user2 = userRepository.save(createAsShopkeeper(USER_NAME + "2", USER_EMAIL + "2"));

        wallet1 = walletRepository.save(createWallet(user1, BigDecimal.TEN));
        wallet2 = walletRepository.save(createWallet(user2, BigDecimal.TEN));
    }

    @Test
    void shouldMakeTransfer() {
        TransferDTO transferDTO = transferService.create(CreateTransferDTO.builder()
                .amount(BigDecimal.TEN)
                .walletFromId(wallet1.getId())
                .walletToId(wallet2.getId())
                .build());

        assertNotNull(transferDTO);
        assertEquals(BigDecimal.TEN, transferDTO.getAmount());
        assertEquals(wallet1.getId(), transferDTO.getWalletFromId());
        assertEquals(wallet2.getId(), transferDTO.getWalletToId());

        assertEquals(
                BigDecimal.ZERO,
                walletRepository.findById(wallet1.getId()).orElseThrow().getAmount());
        assertEquals(
                new BigDecimal("20"),
                walletRepository.findById(wallet2.getId()).orElseThrow().getAmount());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldThowExceptionBecauseValidationError() {
        var wallet1Amount =
                walletRepository.findById(wallet1.getId()).orElseThrow().getAmount();
        var wallet2Amount =
                walletRepository.findById(wallet2.getId()).orElseThrow().getAmount();

        assertThrows(BusinessRuleException.class, () -> {
            transferService.create(CreateTransferDTO.builder()
                    .amount(BigDecimal.TEN)
                    .walletFromId(wallet2.getId())
                    .walletToId(wallet1.getId())
                    .build());
        });

        assertEquals(
                wallet1Amount,
                walletRepository.findById(wallet1.getId()).orElseThrow().getAmount());
        assertEquals(
                wallet2Amount,
                walletRepository.findById(wallet2.getId()).orElseThrow().getAmount());
        assertEquals(0, transferRepository.findAll().size());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldThowExceptionBecauseErrorTransfer() {
        var wallet1Amount =
                walletRepository.findById(wallet1.getId()).orElseThrow().getAmount();
        var wallet2Amount =
                walletRepository.findById(wallet2.getId()).orElseThrow().getAmount();

        doThrow(RuntimeException.class).when(walletService).update(eq(wallet2.getId()), any());

        assertThrows(RuntimeException.class, () -> {
            transferService.create(CreateTransferDTO.builder()
                    .amount(BigDecimal.TEN)
                    .walletFromId(wallet1.getId())
                    .walletToId(wallet2.getId())
                    .build());
        });

        assertEquals(
                wallet1Amount,
                walletRepository.findById(wallet1.getId()).orElseThrow().getAmount());
        assertEquals(
                wallet2Amount,
                walletRepository.findById(wallet2.getId()).orElseThrow().getAmount());
        assertEquals(0, transferRepository.findAll().size());
    }

    @Test
    void shouldThrowExceptionBecauseWalletFromDoesNotHaveFunds() {
        assertThrows(BusinessRuleException.class, () -> {
            transferService.create(CreateTransferDTO.builder()
                    .amount(new BigDecimal("20"))
                    .walletFromId(wallet1.getId())
                    .walletToId(wallet2.getId())
                    .build());
        });
    }
}
