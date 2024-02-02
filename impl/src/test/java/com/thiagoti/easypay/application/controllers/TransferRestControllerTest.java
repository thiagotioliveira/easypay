package com.thiagoti.easypay.application.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiagoti.easypay.api.model.CreateTransferRequestBody;
import com.thiagoti.easypay.domain.dto.CreateUserDTO;
import com.thiagoti.easypay.domain.dto.UserDTO;
import com.thiagoti.easypay.domain.entities.User.Role;
import com.thiagoti.easypay.domain.services.UserService;
import com.thiagoti.easypay.domain.services.WalletService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
class TransferRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO payer;
    private UserDTO payee;
    private UserDTO merchant;

    @BeforeAll
    void setUp() throws Exception {
        this.payer = userService.create(CreateUserDTO.builder()
                .amount(BigDecimal.TEN)
                .cpfCnpj("99999999999")
                .email("mock-payer@test.test")
                .name("Mock Payer")
                .password("mock-payer")
                .role(Role.USER)
                .build());

        this.payee = userService.create(CreateUserDTO.builder()
                .amount(BigDecimal.TEN)
                .cpfCnpj("88888888888")
                .email("mock-payee@test.test")
                .name("Mock Payee")
                .password("mock-payee")
                .role(Role.USER)
                .build());

        this.merchant = userService.create(CreateUserDTO.builder()
                .amount(BigDecimal.TEN)
                .cpfCnpj("99999999999999")
                .email("mock-merchant@test.test")
                .name("Mock Merchant")
                .password("mock-merchant")
                .role(Role.MERCHANT)
                .build());
    }

    @Test
    void testPayerDoesNotExist() throws Exception {
        final CreateTransferRequestBody body = new CreateTransferRequestBody();
        final UUID payeeUserId = UUID.randomUUID();
        final UUID payerUserId = UUID.randomUUID();
        body.setPayee(payeeUserId);
        body.setPayer(payerUserId);
        body.setValue(10d);

        mockMvc.perform(post("/v1/transfers")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(String.format("user '%s' not found.", payerUserId)));
    }

    @Test
    void testPayeeDoesNotExist() throws Exception {
        final UUID payeeUserId = UUID.randomUUID();

        final CreateTransferRequestBody body = new CreateTransferRequestBody();
        body.setPayee(payeeUserId);
        body.setPayer(payer.getId());
        body.setValue(10d);

        mockMvc.perform(post("/v1/transfers")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(String.format("user '%s' not found.", payeeUserId)));
    }

    @Test
    void testTransferBetweenUsersDone() throws Exception {
        final CreateTransferRequestBody body = new CreateTransferRequestBody();
        body.setPayee(payee.getId());
        body.setPayer(payer.getId());
        body.setValue(10d);

        mockMvc.perform(post("/v1/transfers")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testTransferNegativeValue() throws Exception {
        final CreateTransferRequestBody body = new CreateTransferRequestBody();
        body.setPayee(payee.getId());
        body.setPayer(payer.getId());
        body.setValue(-10d);

        mockMvc.perform(post("/v1/transfers")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("invalid operation."));
    }

    @Test
    void testInsufficientFunds() throws Exception {
        final CreateTransferRequestBody body = new CreateTransferRequestBody();
        body.setPayee(payee.getId());
        body.setPayer(payer.getId());

        BigDecimal amount = walletService.getByUser(payer.getId()).orElseThrow().getAmount();
        body.setValue(amount.add(BigDecimal.ONE).doubleValue());

        mockMvc.perform(post("/v1/transfers")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message").value(String.format("insufficient funds for user '%s'", payer.getId())));
    }

    @Test
    void testTransferFromMerchant() throws Exception {
        final CreateTransferRequestBody body = new CreateTransferRequestBody();
        body.setPayee(payee.getId());
        body.setPayer(merchant.getId());
        body.setValue(1d);

        mockMvc.perform(post("/v1/transfers")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("invalid operation."));
    }

    @Test
    void testTransferWithMerchantDone() throws Exception {
        final CreateTransferRequestBody body = new CreateTransferRequestBody();
        body.setPayee(merchant.getId());
        body.setPayer(payer.getId());
        body.setValue(1d);

        mockMvc.perform(post("/v1/transfers")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
