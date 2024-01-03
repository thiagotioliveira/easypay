package com.thiagoti.easypay.movement;

import com.thiagoti.easypay.model.CreateMovementDTO;
import com.thiagoti.easypay.model.MovementDTO;
import java.util.Optional;
import java.util.UUID;

public interface MovementService {

    MovementDTO create(CreateMovementDTO dto);

    Optional<MovementDTO> getById(UUID id);
}
