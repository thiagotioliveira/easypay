package com.thiagoti.easypay.rest;

import com.thiagoti.easypay.domain.TransferService;
import com.thiagoti.easypay.domain.UserService;
import com.thiagoti.easypay.domain.dto.CreateTransferDTO;
import com.thiagoti.easypay.domain.dto.TransferDTO;
import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import com.thiagoti.easypay.spec.api.TransfersApi;
import com.thiagoti.easypay.spec.api.dto.CreateTransferRequestBody;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(
                        () -> new BusinessRuleException("user '%s' not found.", createTransferRequestBody.getPayer()));
        var payee = userService
                .getById(createTransferRequestBody.getPayee())
                .orElseThrow(
                        () -> new BusinessRuleException("user '%s' not found.", createTransferRequestBody.getPayee()));
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
