package mbd.dev.hackmorelos.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.logging.Logger;

@RestControllerAdvice

public class GlobalExceptionHandler {

    Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        logger.warning(ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(
                        null, true, 500, "Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.warning(ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(
                        null, true, 400, "Bad request : " + Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleExpiredJwtException(ExpiredJwtException ex) {
        logger.warning(ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(
                        null, true, 401, "Token is expired"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleMalformedJwtException(MalformedJwtException ex) {
        logger.warning(ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(
                        null, true, 401, "Token is malformed"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponse<String>> handleSignatureException(SignatureException ex) {
        logger.warning(ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(
                        null, true, 401, "Token is invalid signature"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        logger.warning(ex.getMessage());
        return new ResponseEntity<>(
                new ApiResponse<>(
                        null, true, 401, "Token is unsupported"),
                HttpStatus.UNAUTHORIZED
        );
    }


}
