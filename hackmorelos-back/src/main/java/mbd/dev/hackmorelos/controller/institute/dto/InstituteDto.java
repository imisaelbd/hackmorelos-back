package mbd.dev.hackmorelos.controller.institute.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mbd.dev.hackmorelos.model.ubication.Location;

@Data

public class InstituteDto {

    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotBlank(message = "El teléfono es requerido")
    private Long phone;

    @NotBlank(message = "El sitio web es requerido")
    private String site;

    @NotBlank(message = "El tip de institucion es requerido")
    private String type;

    @NotBlank(message = "El nivel de institucion es requerido")
    private String level;

    @NotBlank(message = "El correo es requerido")
    private String email;

    @NotNull(message = "La ubicacion es requerida")
    private Location location;

}
