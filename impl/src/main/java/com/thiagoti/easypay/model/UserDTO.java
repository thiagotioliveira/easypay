package com.thiagoti.easypay.model;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDTO {

    private UUID id;

    private String name;

    private String cpfCnpj;

    private String email;

    private Role role;

    public boolean isUser() {
        return Role.USER.equals(this.role);
    }

    public enum Role {
        USER,
        SHOPKEEPER
    }
}
