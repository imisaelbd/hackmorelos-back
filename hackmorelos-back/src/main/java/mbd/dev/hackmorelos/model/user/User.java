package mbd.dev.hackmorelos.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import mbd.dev.hackmorelos.model.role.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "users")

public class User {

    @Id
    private String id;

    private String fullName;

    private String email;

    @JsonIgnore
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt = LocalDate.now();

    private boolean enabled;

    private boolean blocked;

    @DBRef
    private Role role;

}
