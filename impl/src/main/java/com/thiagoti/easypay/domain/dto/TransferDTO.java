package com.thiagoti.easypay.domain.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TransferDTO {

    private UUID id;

    private OffsetDateTime createdAt;

    private UUID walletFromId;

    private UUID walletToId;

    private BigDecimal amount;
}
