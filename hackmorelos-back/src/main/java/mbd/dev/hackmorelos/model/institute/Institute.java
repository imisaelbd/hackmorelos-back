package mbd.dev.hackmorelos.model.institute;

import lombok.Data;
import mbd.dev.hackmorelos.model.ubication.Location;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "institutes")

public class Institute {

    @Id
    private String id;

    private String name;

    private Long phone;

    private String site;

    private String type;

    private String level;

    private String email;

    private Location location;

}
