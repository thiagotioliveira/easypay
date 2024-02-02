package com.thiagoti.easypay.external.transferauthorizer;

import com.thiagoti.easypay.external.transferauthorizer.dto.TransferAuthorizerStatusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "transferAuthorizerClient", url = "${app.external.transfer-authorizer.url}")
public interface TransferAuthorizerClient {

    @GetMapping
    TransferAuthorizerStatusDTO get();
}
