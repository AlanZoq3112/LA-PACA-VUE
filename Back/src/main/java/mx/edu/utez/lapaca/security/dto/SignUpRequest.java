package mx.edu.utez.lapaca.security.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import mx.edu.utez.lapaca.dto.usuarios.constraints.IsAdult;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.utils.Validations;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data

public class SignUpRequest {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener como máximo {max} caracteres")
    private String nombre;

    @NotBlank(message = "El genero es obligatorio")
    @Size(max = 15, message = "El genero debe tener como máximo {max} caracteres")
    private String genero;

    @NotBlank(message = "La URL de la imagen no puede estar vacía")
    private String imagenUrl;

    @NotNull(message = "La dirección de correo electrónico es obligatoria")
    @Email(message = "La dirección de correo electrónico debe ser válida")
    private String email;

    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 25, message = "La contraseña debe tener entre {min} y {max} caracteres")
    private String password;


    @NotNull(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{1,10}$", message = "El teléfono debe contener solo números y tener como máximo 10 dígitos")
    private String telefono;



    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @DateTimeFormat(pattern = "yyyy/mm/dd")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @IsAdult
    private LocalDate fechaNacimiento;


}
