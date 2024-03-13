package mx.edu.utez.lapaca.security.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpRequest {
    private String nombre;
    private String imagen_url;
    private String email;
    private String password;
    private Long telefono;
    private Date fechaNacimiento;


}
