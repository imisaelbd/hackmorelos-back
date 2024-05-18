package mbd.dev.hackmorelos.model.file;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileResository extends MongoRepository<File, String> {
}
