package mbd.dev.hackmorelos.controller.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class RoleDto {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotBlank(message = "La descripcion es requerida")
    private String description;
}
