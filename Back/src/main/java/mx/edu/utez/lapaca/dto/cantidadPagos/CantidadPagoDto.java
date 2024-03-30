package mx.edu.utez.lapaca.dto.cantidadPagos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.cantidadPago.CantidadPago;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CantidadPagoDto {

    private Long id;
    private Double monto;
    private Producto producto;
    private Pago pago;
    private Usuario usuario;

    public CantidadPago getCantidadPago() {
        return new CantidadPago(
                getId(),
                getMonto(),
                getProducto(),
                getPago(),
                getUsuario()
        );
    }

}
