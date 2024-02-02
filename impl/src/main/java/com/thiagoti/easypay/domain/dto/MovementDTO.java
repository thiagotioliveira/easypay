package com.thiagoti.easypay.domain.dto;

import com.thiagoti.easypay.domain.entities.Movement.Type;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class MovementDTO {

    private UUID id;

    private OffsetDateTime createdAt;

    private UUID walletId;

    private Type type;

    private BigDecimal amount;
}
