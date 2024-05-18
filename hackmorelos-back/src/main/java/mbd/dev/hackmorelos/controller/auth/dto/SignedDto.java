package mbd.dev.hackmorelos.controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mbd.dev.hackmorelos.model.user.User;

@Data
@AllArgsConstructor

public class SignedDto {

    private String token;

    private User user;
}
