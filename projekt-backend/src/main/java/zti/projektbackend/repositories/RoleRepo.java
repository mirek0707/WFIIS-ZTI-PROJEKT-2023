package zti.projektbackend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import zti.projektbackend.models.EnumRole;
import zti.projektbackend.models.Role;

public interface RoleRepo extends MongoRepository<Role, String> {
    Optional<Role> findByName(EnumRole name);
}
