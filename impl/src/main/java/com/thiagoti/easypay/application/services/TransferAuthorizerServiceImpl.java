package com.thiagoti.easypay.application.services;

import com.thiagoti.easypay.domain.exceptions.BusinessRuleException;
import com.thiagoti.easypay.domain.services.TransferAuthorizerService;
import com.thiagoti.easypay.external.transferauthorizer.TransferAuthorizerClient;
import com.thiagoti.easypay.external.transferauthorizer.dto.TransferAuthorizerStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class TransferAuthorizerServiceImpl implements TransferAuthorizerService {

    private final TransferAuthorizerClient client;

    @Override
    public void authorize() {
        TransferAuthorizerStatusDTO statusDTO = client.get();
        if (statusDTO.isInvalid()) {
            throw new BusinessRuleException("transfer does not authorized.");
        }
    }
}
