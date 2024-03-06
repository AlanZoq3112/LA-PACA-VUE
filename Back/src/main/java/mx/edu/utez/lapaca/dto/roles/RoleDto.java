package mx.edu.utez.lapaca.dto.roles;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.roles.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 15, message = "El nombre debe tener como máximo {max} caracteres")
    private String nombre;

    public Role getRole() {
        return new Role(
                getId(),
                getNombre()
        );
    }

}
