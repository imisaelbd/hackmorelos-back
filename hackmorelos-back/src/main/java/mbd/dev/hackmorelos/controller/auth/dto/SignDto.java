package mbd.dev.hackmorelos.controller.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class SignDto {

    @NotBlank(message = "El email es requerido")
    private String email;

    @NotBlank(message = "La cotraseña es requerida")
    private String password;
}
