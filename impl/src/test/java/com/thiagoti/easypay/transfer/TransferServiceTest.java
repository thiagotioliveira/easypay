package com.thiagoti.easypay.transfer;

import static com.thiagoti.easypay.mock.UserMock.USER_EMAIL;
import static com.thiagoti.easypay.mock.UserMock.USER_NAME;
import static com.thiagoti.easypay.mock.UserMock.createAsMerchant;
import static com.thiagoti.easypay.mock.UserMock.createAsUser;
import static com.thiagoti.easypay.mock.WalletMock.createWallet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.thiagoti.easypay.exception.BusinessRuleException;
import com.thiagoti.easypay.external.TransferAuthorizerClient;
import com.thiagoti.easypay.external.model.TransferAuthorizerStatusDTO;
import com.thiagoti.easypay.model.CreateTransferDTO;
import com.thiagoti.easypay.model.TransferDTO;
import com.thiagoti.easypay.model.entity.User;
import com.thiagoti.easypay.model.entity.Wallet;
import com.thiagoti.easypay.movement.MovementRepository;
import com.thiagoti.easypay.user.UserMapper;
import com.thiagoti.easypay.user.UserRepository;
import com.thiagoti.easypay.wallet.WalletRepository;
import com.thiagoti.easypay.wallet.WalletService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class TransferServiceTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private MovementRepository movementRepository;

    @MockBean
    private TransferAuthorizerClient transferAuthorizerClient;

    @SpyBean
    private WalletService walletService;

    private Wallet walletUser;
    private Wallet walletShopkeeper;

    private User userAsUser;
    private User userAsShopkeeper;

    @BeforeEach
    void setUp() throws Exception {
        transferRepository.deleteAll();
        movementRepository.deleteAll();
        walletRepository.deleteAll();
        userRepository.deleteAll();

        userAsUser = userRepository.save(createAsUser(USER_NAME + "1", USER_EMAIL + "1"));
        userAsShopkeeper = userRepository.save(createAsMerchant(USER_NAME + "2", USER_EMAIL + "2"));

        walletUser = walletRepository.save(createWallet(userAsUser, BigDecimal.TEN));
        walletShopkeeper = walletRepository.save(createWallet(userAsShopkeeper, BigDecimal.TEN));

        when(transferAuthorizerClient.get())
                .thenReturn(TransferAuthorizerStatusDTO.builder()
                        .message(TransferAuthorizerStatusDTO.MESSAGE_AUTHORIZED)
                        .build());
    }

    @Test
    void shouldMakeTransfer() {
        TransferDTO transferDTO = transferService.create(CreateTransferDTO.builder()
                .amount(BigDecimal.TEN)
                .userFrom(userMapper.toDTO(userAsUser))
                .userTo(userMapper.toDTO(userAsShopkeeper))
                .build());

        assertNotNull(transferDTO);
        assertEquals(BigDecimal.TEN, transferDTO.getAmount());
        assertNotNull(transferDTO.getMovementDebitId());
        assertNotNull(transferDTO.getMovementCreditId());
        assertNotEquals(transferDTO.getMovementDebitId(), transferDTO.getMovementCreditId());

        assertEquals(
                new BigDecimal("0.00"),
                walletRepository.findById(walletUser.getId()).orElseThrow().getAmount());
        assertEquals(
                new BigDecimal("20.00"),
                walletRepository
                        .findById(walletShopkeeper.getId())
                        .orElseThrow()
                        .getAmount());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldThowExceptionBecauseValidationError() {
        var wallet1Amount =
                walletRepository.findById(walletUser.getId()).orElseThrow().getAmount();
        var wallet2Amount = walletRepository
                .findById(walletShopkeeper.getId())
                .orElseThrow()
                .getAmount();

        assertThrows(BusinessRuleException.class, () -> {
            transferService.create(CreateTransferDTO.builder()
                    .amount(BigDecimal.TEN)
                    .userFrom(userMapper.toDTO(userAsShopkeeper))
                    .userTo(userMapper.toDTO(userAsUser))
                    .build());
        });

        assertEquals(
                wallet1Amount,
                walletRepository.findById(walletUser.getId()).orElseThrow().getAmount());
        assertEquals(
                wallet2Amount,
                walletRepository
                        .findById(walletShopkeeper.getId())
                        .orElseThrow()
                        .getAmount());
        assertEquals(0, transferRepository.findAll().size());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void shouldThowExceptionBecauseErrorTransfer() {
        var wallet1Amount =
                walletRepository.findById(walletUser.getId()).orElseThrow().getAmount();
        var wallet2Amount = walletRepository
                .findById(walletShopkeeper.getId())
                .orElseThrow()
                .getAmount();

        doThrow(RuntimeException.class).when(walletService).update(eq(walletShopkeeper.getId()), any());

        assertThrows(RuntimeException.class, () -> {
            transferService.create(CreateTransferDTO.builder()
                    .amount(BigDecimal.TEN)
                    .userFrom(userMapper.toDTO(userAsShopkeeper))
                    .userTo(userMapper.toDTO(userAsUser))
                    .build());
        });

        assertEquals(
                wallet1Amount,
                walletRepository.findById(walletUser.getId()).orElseThrow().getAmount());
        assertEquals(
                wallet2Amount,
                walletRepository
                        .findById(walletShopkeeper.getId())
                        .orElseThrow()
                        .getAmount());
        assertEquals(0, transferRepository.findAll().size());
    }

    @Test
    void shouldThrowExceptionBecauseWalletFromDoesNotHaveFunds() {
        assertThrows(BusinessRuleException.class, () -> {
            transferService.create(CreateTransferDTO.builder()
                    .amount(new BigDecimal("20"))
                    .userFrom(userMapper.toDTO(userAsShopkeeper))
                    .userTo(userMapper.toDTO(userAsUser))
                    .build());
        });
    }
}
