package mbd.dev.hackmorelos.controller.institute;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.controller.institute.dto.InstituteDto;
import mbd.dev.hackmorelos.model.institute.Institute;
import mbd.dev.hackmorelos.service.institute.InstituteService;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-alma/institute")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor

public class InstituteController {

    private final InstituteService instituteService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Institute>> create(@Valid @RequestBody InstituteDto institute) {
        try {
            return instituteService.create(institute);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrio un error interno en el servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<java.util.List<Institute>>> getAll() {
        try {
            return instituteService.getAll();
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrio un error interno en el servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
