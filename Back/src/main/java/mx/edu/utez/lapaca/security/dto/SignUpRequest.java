package mx.edu.utez.lapaca.security.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SignUpRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 20, message = "El nombre debe tener como máximo {max} caracteres")
    private String nombre;


    private String imagen_url;


    @NotBlank(message = "La dirección de correo electrónico es obligatoria")
    @Email(message = "La dirección de correo electrónico debe ser válida")
    private String email;


    @Size(min = 8, max = 35, message = "La contraseña debe tener entre {min} y {max} caracteres")
    private String password;


    @NotNull(message = "El teléfono es obligatorio")
    @Digits(integer = 10, fraction = 0, message = "El teléfono debe contener solo números y tener como máximo {integer} dígitos")
    private Long telefono;


    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @DateTimeFormat(pattern = "yyyy/mm/dd")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private Date fechaNacimiento;


}
