package com.thiagoti.easypay.domain;

import com.thiagoti.easypay.domain.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, UUID> {

    List<User> findAll();
}
