package mbd.dev.hackmorelos.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class UserDto {

    @NotBlank(message = "El nombre completo es requerido")
    private String fullName;

    @NotBlank(message = "El email es requerido")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    private String password;
}
