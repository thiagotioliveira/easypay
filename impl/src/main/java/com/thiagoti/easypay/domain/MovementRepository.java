package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.entity.Movement;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MovementRepository extends CrudRepository<Movement, UUID> {}
