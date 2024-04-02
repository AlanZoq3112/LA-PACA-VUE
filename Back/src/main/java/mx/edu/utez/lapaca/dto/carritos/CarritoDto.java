package mx.edu.utez.lapaca.dto.carritos;



import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoDto {

    private Long id;

    private int cantidad;

    private Double monto;


    private Usuario usuario;

    private Producto producto;

    private Pago pago;


    public Carrito getCarrito () {
        return new Carrito(
                getId(),
                getCantidad(),
                getMonto(),
                getUsuario(),
                getProducto(),
                getPago()
        );
    }
}



