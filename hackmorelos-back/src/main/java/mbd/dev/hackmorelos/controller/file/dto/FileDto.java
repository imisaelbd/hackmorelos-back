package mbd.dev.hackmorelos.controller.file.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class FileDto {

    @NotBlank(message = "El título es requerido")
    private String title;

    @NotBlank(message = "La descripción es requerida")
    private String description;
}
