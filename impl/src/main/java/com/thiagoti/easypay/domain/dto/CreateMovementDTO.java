package com.thiagoti.easypay.domain.dto;

import com.thiagoti.easypay.domain.entity.Movement.Type;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
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
public class CreateMovementDTO {

    @NotNull
    private UUID walletId;

    @NotNull
    private Type type;

    @NotNull
    @Positive
    private BigDecimal amount;
}
