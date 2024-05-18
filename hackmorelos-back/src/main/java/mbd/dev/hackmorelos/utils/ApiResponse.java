package mbd.dev.hackmorelos.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponse<T> {

    private T data;

    private boolean error;

    private int statusCode;

    String message;
}
