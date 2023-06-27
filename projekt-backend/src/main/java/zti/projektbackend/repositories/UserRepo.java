package zti.projektbackend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import zti.projektbackend.models.User;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
