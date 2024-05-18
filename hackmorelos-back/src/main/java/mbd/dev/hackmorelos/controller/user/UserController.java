package mbd.dev.hackmorelos.controller.user;

import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.model.user.User;
import mbd.dev.hackmorelos.service.user.UserService;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-alma/user")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<User>>> getAll() {
        try {
            return userService.getAll();
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrio un error interno en el servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<User>> getById(@PathVariable String id) {
        try {
            return userService.getById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrio un error interno en el servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
