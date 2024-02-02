package com.thiagoti.easypay.domain.repositories;

import com.thiagoti.easypay.domain.entities.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

    List<User> findAll();
}
