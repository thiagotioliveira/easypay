package com.thiagoti.easypay.movement;

import com.thiagoti.easypay.model.entity.Movement;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MovementRepository extends CrudRepository<Movement, UUID> {}
