package com.thiagoti.easypay.application.controllers;

import com.thiagoti.easypay.api.TransfersApi;
import com.thiagoti.easypay.api.model.CreateTransferRequestBody;
import com.thiagoti.easypay.domain.dto.CreateTransferDTO;
import com.thiagoti.easypay.domain.dto.TransferDTO;
import com.thiagoti.easypay.domain.exceptions.BusinessRuleException;
import com.thiagoti.easypay.domain.services.TransferService;
import com.thiagoti.easypay.domain.services.UserService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class TransferRestController implements TransfersApi {

    private final UserService userService;
    private final TransferService transService;

    @Override
    public ResponseEntity<Void> createTransfer(@Valid CreateTransferRequestBody createTransferRequestBody) {
        var payer = userService
                .getById(createTransferRequestBody.getPayer())
                .orElseThrow(() -> new BusinessRuleException(
                        HttpStatus.NOT_FOUND, "user '%s' not found.", createTransferRequestBody.getPayer()));
        var payee = userService
                .getById(createTransferRequestBody.getPayee())
                .orElseThrow(() -> new BusinessRuleException(
                        HttpStatus.NOT_FOUND, "user '%s' not found.", createTransferRequestBody.getPayee()));
        TransferDTO transferDto = transService.create(CreateTransferDTO.builder()
                .amount(BigDecimal.valueOf(createTransferRequestBody.getValue()))
                .userFrom(payer)
                .userTo(payee)
                .build());
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(transferDto.getId())
                        .toUri())
                .build();
    }
}
