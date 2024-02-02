package com.thiagoti.easypay.domain.services;

import com.thiagoti.easypay.domain.dto.CreateMovementDTO;
import com.thiagoti.easypay.domain.dto.MovementDTO;
import java.util.Optional;
import java.util.UUID;

public interface MovementService {

    MovementDTO create(CreateMovementDTO dto);

    Optional<MovementDTO> getById(UUID id);
}
