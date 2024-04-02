package mx.edu.utez.lapaca.dto.direcciones;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DireccionDto {

    private Long id;

    @NotBlank(message = "la calle es obligatoria")
    @Size(max = 25, message = "La calle debe tener como máximo {max} caracteres")
    private String calle;

    @NotNull(message = "El numero es obligatorio")
    @Positive(message = "El numero de la calle debe ser un número positivo")
    private int numero;

    @NotBlank(message = "la colonia es obligatoria")
    @Size(max = 25, message = "La colonia debe tener como máximo {max} caracteres")
    private String colonia;

    @NotBlank(message = "el estado es obligatorio")
    @Size(max = 15, message = "El estado debe tener como máximo {max} caracteres")
    private String estado;

    @NotBlank(message = "el municipio es obligatorio")
    @Size(max = 15, message = "El municipio debe tener como máximo {max} caracteres")
    private String municipio;

    @NotNull(message = "El código postal es obligatorio")
    @Size(min = 5, max = 5, message = "El código postal debe tener exactamente 5 dígitos")
    private String codigoPostal;

    @NotBlank(message = "La referencia es obligatoria")
    @Size(max = 100, message = "el cp debe tener {max} caracteres")
    private String referencia;



    private Usuario usuario;

    public Direccion getDireccion() {
        return new Direccion(
                getId(),
                getCalle(),
                getNumero(),
                getColonia(),
                getEstado(),
                getMunicipio(),
                getCodigoPostal(),
                getReferencia(),
                getUsuario()
        );
    }



}
