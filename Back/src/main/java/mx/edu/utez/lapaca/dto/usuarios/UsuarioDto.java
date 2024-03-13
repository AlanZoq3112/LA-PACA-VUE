package mx.edu.utez.lapaca.dto.usuarios;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDto {

    private Long id;

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


    private Role role;

    private List<Direccion> direcciones;

    private List<Pago> pagos;

    private List<Producto> productos;

    public Usuario getUsuario() {
        return new Usuario(
                getId(),
                getNombre(),
                getImagen_url(),
                getEmail(),
                getPassword(),
                getTelefono(),
                getFechaNacimiento(),
                getDirecciones(),
                getPagos(),
                getProductos(),
                getRole()
        );
    }
}
