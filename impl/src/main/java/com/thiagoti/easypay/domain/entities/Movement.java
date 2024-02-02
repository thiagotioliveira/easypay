package com.thiagoti.easypay.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "wallet_id")
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private Type type;

    @Positive
    @Column(nullable = false)
    private BigDecimal amount;

    public boolean isDebit() {
        return Type.DEBIT.equals(this.type);
    }

    public enum Type {
        CREDIT,
        DEBIT;
    }
}
