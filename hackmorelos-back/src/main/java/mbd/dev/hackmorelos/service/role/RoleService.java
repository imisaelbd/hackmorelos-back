package mbd.dev.hackmorelos.service.role;

import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.controller.role.dto.RoleDto;
import mbd.dev.hackmorelos.model.role.Role;
import mbd.dev.hackmorelos.model.role.RoleRepository;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = {MongoException.class})
    public ResponseEntity<ApiResponse<Role>> create (RoleDto role) {
        if (roleRepository.existsByName(role.getName())) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.BAD_REQUEST.value(), "El rol ya se encuentra registrado"),
                    HttpStatus.BAD_REQUEST);
        }
        Role newRole = new Role();
        String newId = String.valueOf(UUID.randomUUID());
        newRole.setId(newId);
        BeanUtils.copyProperties(role, newRole);
        Role savedRole = roleRepository.save(newRole);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        savedRole, false, HttpStatus.OK.value(), "Rol creado correctamente"),
                HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<Role>>> getAll () {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.NOT_FOUND.value(), "No se encontraron roles"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                new ApiResponse<>(
                        roles, false, HttpStatus.OK.value(), "Rol no encontrado"),
                HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Role>> getById (String id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            role, false, HttpStatus.OK.value(), "Rol encontrado"),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new ApiResponse<>(
                        null, true, HttpStatus.NOT_FOUND.value(), "Rol no encontrado"),
                HttpStatus.NOT_FOUND);
    }

}
