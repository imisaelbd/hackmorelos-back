package mbd.dev.hackmorelos.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.controller.auth.dto.SignDto;
import mbd.dev.hackmorelos.controller.auth.dto.SignedDto;
import mbd.dev.hackmorelos.controller.user.dto.UserDto;
import mbd.dev.hackmorelos.model.user.User;
import mbd.dev.hackmorelos.service.auth.AuthService;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-alma/auth")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PutMapping("/signIn")
    public ResponseEntity<ApiResponse<SignedDto>> signIn(@Valid @RequestBody SignDto user) {
        try {
            return authService.signIn(user);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrion un error interno en el servidor"),
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse<User>> create(@Valid @RequestBody UserDto user) {
        try {
            return authService.createUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrion un error interno en el servidor"),
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
