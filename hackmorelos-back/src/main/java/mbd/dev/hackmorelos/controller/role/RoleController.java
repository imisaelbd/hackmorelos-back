package mbd.dev.hackmorelos.controller.role;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.controller.role.dto.RoleDto;
import mbd.dev.hackmorelos.model.role.Role;
import mbd.dev.hackmorelos.service.role.RoleService;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-alma/role")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor


public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Role>> create(@Valid @RequestBody RoleDto role) {
        try {
            return roleService.create(role);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrio un error interno en el servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Role>>> getAll() {
        try {
            return roleService.getAll();
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrio un error interno en el servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<Role>> getById(@PathVariable String id) {
        try {
            return roleService.getById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrio un error interno en el servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
