package com.thiagoti.easypay.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
public class Transfer {

	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
	
	@Column(nullable = false)
	private OffsetDateTime createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "wallet_from_id")
    private Wallet walletFrom;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "wallet_to_id")
	private Wallet walletTo;
	
	@Positive
    @Column(nullable = false)
    private BigDecimal amount;
	
	@AssertTrue
	private boolean isValid() {
		return Boolean.FALSE.equals(this.walletFrom.equals(this.walletTo));
	}
}
