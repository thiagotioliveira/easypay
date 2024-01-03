package com.thiagoti.easypay.user;

import com.thiagoti.easypay.model.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

    List<User> findAll();
}
