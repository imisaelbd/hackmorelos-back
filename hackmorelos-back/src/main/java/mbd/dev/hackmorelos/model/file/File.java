package mbd.dev.hackmorelos.model.file;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "files")

public class File {

    @Id
    private String id;

    private String url;

    private String title;

    private String description;
}
