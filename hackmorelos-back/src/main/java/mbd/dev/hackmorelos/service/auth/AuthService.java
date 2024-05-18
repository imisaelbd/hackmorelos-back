package mbd.dev.hackmorelos.service.auth;

import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.controller.auth.dto.SignDto;
import mbd.dev.hackmorelos.controller.auth.dto.SignedDto;
import mbd.dev.hackmorelos.controller.user.dto.UserDto;
import mbd.dev.hackmorelos.model.role.Role;
import mbd.dev.hackmorelos.model.role.RoleRepository;
import mbd.dev.hackmorelos.model.user.User;
import mbd.dev.hackmorelos.model.user.UserRepository;
import mbd.dev.hackmorelos.security.jwt.JwtProvider;
import mbd.dev.hackmorelos.security.service.UserDetailsServiceImpl;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${role.admin}")
    private String adminRole;
    @Value("${role.user}")
    private String roleUser;

    private User userCreate (UserDto user, Role role) {
        User newUser = new User();
        String newId = String.valueOf(java.util.UUID.randomUUID());
        newUser.setId(newId);
        newUser.setBlocked(true);
        newUser.setEnabled(true);
        newUser.setRole(role);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        BeanUtils.copyProperties(user, newUser, "password");
        return userRepository.save(newUser);
    }


    @Transactional(rollbackFor = {MongoException.class})
    public ResponseEntity<ApiResponse<User>> createUser (UserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.BAD_REQUEST.value(), "El email ya se encuentra registrado"
                    ),
                    HttpStatus.BAD_REQUEST);
        }

        Role userRole = roleRepository.findByName(roleUser).orElse(null);
        if (userRole == null) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.NOT_FOUND.value(), "El rol usuario no ha sido encontrado"
                    ),
                    HttpStatus.NOT_FOUND);
        }
        User newUser = userCreate(user, userRole);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        newUser, false, HttpStatus.OK.value(), "Usuario registrado correctamente"
                ),
                HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<SignedDto>> signIn (SignDto user) {
        try {
            User userFound = userRepository.findByEmail(user.getEmail()).orElse(null);
            if (userFound == null)
                return new ResponseEntity<>(
                        new ApiResponse<>(
                                null, true, HttpStatus.NOT_FOUND.value(), "Emial no encontrado"
                        ),
                        HttpStatus.NOT_FOUND
                );
            if (!userFound.isEnabled())
                return new ResponseEntity<>(
                        new ApiResponse<>(null, true, HttpStatus.UNAUTHORIZED.value(), "Usuario no habilitado"
                        ),
                        HttpStatus.UNAUTHORIZED
                );
            if (!userFound.isBlocked())
                return new ResponseEntity<>(
                        new ApiResponse<>(
                                null, true, HttpStatus.UNAUTHORIZED.value(), "Userio bloqueado"
                        ),
                        HttpStatus.UNAUTHORIZED);
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtProvider.generateToken(userDetails);
            SignedDto signedDto = new SignedDto(token, userFound);
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            signedDto, false, HttpStatus.OK.value(), "Inicio de sesión correcto"),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.BAD_REQUEST.value(), "Credeciales incorrectas"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
