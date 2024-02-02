package com.thiagoti.easypay.domain.repositories;

import com.thiagoti.easypay.domain.entities.Movement;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MovementRepository extends CrudRepository<Movement, UUID> {}
