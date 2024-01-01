package com.thiagoti.easypay.domain.dto;

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
public class UserDTO {

    private UUID id;

    private String name;

    private String cpfCnpj;

    private String email;

    private Role role;

    public enum Role {
        USER,
        SHOPKEEPER
    }
}
