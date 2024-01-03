package com.thiagoti.easypay.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
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
public class CreateWalletDTO {

    @NotNull
    private UserDTO user;

    @PositiveOrZero
    @Digits(fraction = 2, integer = Integer.MAX_VALUE)
    private BigDecimal amount;
}
