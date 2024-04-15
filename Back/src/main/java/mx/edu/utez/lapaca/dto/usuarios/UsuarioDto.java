package mx.edu.utez.lapaca.dto.usuarios;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.dto.usuarios.constraints.IsAdult;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.vendedores.Vendedor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDto {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50,min = 10, message = "El nombre debe tener entre {min} y {max} caracteres")
    private String nombre;

    @NotBlank(message = "El genero es obligatorio")
    @Size(max = 15, message = "El genero debe tener como máximo {max} caracteres")
    private String genero;

    private MultipartFile image;

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

    private String secretPass;

    private Role role;

    private Vendedor vendedor;

    private List<Direccion> direcciones;

    public Usuario getUsuario() {

        return new Usuario(
                getId(),
                getNombre(),
                getGenero(),
                getImage().getResource().getFilename(),
                getEmail(),
                getPassword(),
                getTelefono(),
                getFechaNacimiento(),
                getSecretPass(),
                getRole(),
                getVendedor(),
                getDirecciones()
        );
    }
}
