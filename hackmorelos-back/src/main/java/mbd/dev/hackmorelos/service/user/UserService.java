package mbd.dev.hackmorelos.service.user;

import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.model.user.User;
import mbd.dev.hackmorelos.model.user.UserRepository;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<User>>> getAll () {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.NOT_FOUND.value(), "No se encontraron usuarios"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                new ApiResponse<>(
                        users, false, HttpStatus.FOUND.value(), "Usuarios encontrados"),
                HttpStatus.FOUND);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<User>> getById (String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            user, false, HttpStatus.FOUND.value(), "Usuario encontrado"),
                    HttpStatus.FOUND);
        }
        return new ResponseEntity<>(
                new ApiResponse<>(
                        null, true, HttpStatus.NOT_FOUND.value(), "Usuario no encontrado"),
                HttpStatus.NOT_FOUND);
    }


}
