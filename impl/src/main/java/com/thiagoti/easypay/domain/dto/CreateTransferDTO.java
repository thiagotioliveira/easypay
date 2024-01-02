package com.thiagoti.easypay.domain.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class CreateTransferDTO {

    @NotNull
    private UserDTO userFrom;

    @NotNull
    private UserDTO userTo;

    @Positive
    private BigDecimal amount;

    @AssertTrue(message = "there must be different users")
    private boolean hasDifferentUsers() {
        return Boolean.FALSE.equals(this.userFrom.equals(this.userTo));
    }

    @AssertTrue(message = "the payer must be a user")
    private boolean isUserFromAsUser() {
        return this.userFrom.isUser();
    }
}
