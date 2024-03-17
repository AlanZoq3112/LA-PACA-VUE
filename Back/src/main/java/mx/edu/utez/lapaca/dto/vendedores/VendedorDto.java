package mx.edu.utez.lapaca.dto.vendedores;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.vendedores.Vendedor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendedorDto {

    private Long id;

    @NotNull(message = "La CURP es obligatoria")
    @Size(min = 18, max = 18, message = "La CURP debe tener exactamente 18 caracteres")
    private String curp;

    @NotNull(message = "El teléfono es obligatorio")
    @Digits(integer = 10, fraction = 0, message = "El teléfono debe contener solo números y tener como máximo {integer} dígitos")
    private Long telefonoVendedor;

    @NotBlank(message = "La URL de la imagen ine no puede estar vacía")
    private String ine;

    @NotBlank(message = "El RFC no puede estar vacío")
    @Pattern(regexp = "^[A-Z]{4}[0-9]{6}[A-Z0-9]{3}$", message = "RFC no válido")
    private String rfc;


    @NotBlank(message = "El nombre es obligatorio")
    private Usuario usuario;

    public Vendedor getVendedor() {
        return new Vendedor(
                getId(),
                getCurp(),
                getTelefonoVendedor(),
                getIne(),
                getRfc(),
                getUsuario()
        );
    }
}
