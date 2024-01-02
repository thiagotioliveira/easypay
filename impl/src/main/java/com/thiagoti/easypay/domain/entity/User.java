package com.thiagoti.easypay.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "user1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @Column(length = 100)
    private String name;

    @Column(length = 14, unique = true)
    private String cpfCnpj;

    @Column(length = 255, unique = true)
    private String email;

    @Column(length = 50)
    @ToString.Exclude
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Role role;

    public enum Role {
        USER,
        SHOPKEEPER
    }
}
