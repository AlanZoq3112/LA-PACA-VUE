package mx.edu.utez.lapaca.dto.pagos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.pagos.TipoPago;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagoDto {

    private Long id;

    @NotBlank(message = "Debes seleccionar un tipo de pago válido")
    private TipoPago tipo;

    @NotNull(message = "Este campo es obligatorio")
    @NotBlank(message = "No puede contener espacios en blanco")
    @Size(min = 13, max = 19, message = "El número de tarjeta debe tener entre {min} y {max} dígitos")
    @Pattern(regexp = "\\d+", message = "El número de tarjeta debe contener solo dígitos")
    private Long numero;

    @NotNull(message = "Este campo es obligatorio")
    @Size(min = 3, max = 4, message = "El CVV debe tener entre {min} y {max} dígitos")
    @Pattern(regexp = "\\d+", message = "El CVV debe contener solo dígitos")
    private Integer cvv;

    @NotBlank(message = "Este campo es obligatorio")
    @Size(max = 50, message = "El titular de la tarjeta no puede exceder los {max} caracteres")
    private String titular;

    @NotNull(message = "Este campo es obligatorio")
    @Size(min = 5, max = 5, message = "La fecha de vencimiento debe tener 5 caracteres")
    @Pattern(regexp = "\\d{2}/\\d{2}", message = "El formato de la fecha de vencimiento debe ser MM/YY")
    private String fechaVencimiento;

    @NotNull(message = "El id de usuario no puede ser nulo")
    private Usuario usuario;

    public Pago getPago() {
        return new Pago(
                getId(),
                getTipo(),
                getNumero(),
                getCvv(),
                getTitular(),
                getFechaVencimiento(),
                getUsuario()
        );
    }



}
