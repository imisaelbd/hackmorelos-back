package mbd.dev.hackmorelos.model.institute;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InstituteRepository extends MongoRepository<Institute, String> {

    boolean existsByName(String name);

}
