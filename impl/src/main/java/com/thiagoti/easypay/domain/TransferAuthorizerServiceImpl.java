package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.exception.BusinessRuleException;
import com.thiagoti.easypay.external.TransferAuthorizerClient;
import com.thiagoti.easypay.external.dto.TransferAuthorizerStatusDTO;
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
