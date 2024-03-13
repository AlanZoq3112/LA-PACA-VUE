package mx.edu.utez.lapaca.security.dto;

import lombok.Data;

@Data
public class SinginRequest {
    private String email;
    private String password;
}
