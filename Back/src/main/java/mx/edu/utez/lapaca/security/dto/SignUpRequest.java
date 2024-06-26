package mx.edu.utez.lapaca.security.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import mx.edu.utez.lapaca.dto.usuarios.constraints.IsAdult;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data

public class SignUpRequest {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener como máximo {max} caracteres")
    private String nombre;

    @NotBlank(message = "El genero es obligatorio")
    @Size(max = 15, message = "El genero debe tener como máximo {max} caracteres")
    private String genero;

    @NotBlank(message = "La imagen no puede estar vacía")
    private String image;

    @NotNull(message = "La dirección de correo electrónico es obligatoria")
    @Email(message = "La dirección de correo electrónico debe ser válida")
    private String email;

    @Size(min = 8, max = 35, message = "La contraseña debe tener entre {min} y {max} caracteres")
    private String password;

    @NotNull(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\d{1,10}$", message = "El teléfono debe contener solo números y tener como máximo 10 dígitos")
    private String telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @DateTimeFormat(pattern = "yyyy/mm/dd")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @IsAdult
    private LocalDate fechaNacimiento;


}
