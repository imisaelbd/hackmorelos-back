package mbd.dev.hackmorelos.model.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String username);
}
