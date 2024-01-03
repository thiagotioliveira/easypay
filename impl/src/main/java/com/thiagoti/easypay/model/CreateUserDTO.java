package com.thiagoti.easypay.model;

import com.thiagoti.easypay.model.entity.User.Role;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
public class CreateUserDTO {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(min = 11, max = 14)
    @Pattern(regexp = "[\\d]{11}|[\\d]{14}")
    private String cpfCnpj;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    @ToString.Exclude
    private String password;

    @NotNull
    private Role role;

    @PositiveOrZero
    @Digits(fraction = 2, integer = Integer.MAX_VALUE)
    private BigDecimal amount;
}
