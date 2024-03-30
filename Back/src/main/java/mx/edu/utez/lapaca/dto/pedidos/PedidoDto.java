package mx.edu.utez.lapaca.dto.pedidos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.pedidos.Pedido;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDto {

    private Long id;

    @NotNull(message = "El id de usuario no puede ser nulo")
    private Usuario usuario;

    @NotNull(message = "El id de producto no puede ser nulo")
    private Producto producto;

    @NotNull(message = "El id pago no puede ser nulo")
    private Pago pago;

    @NotNull(message = "id dirección es obligatorio")
    private Direccion direccion;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean status;


    public Pedido getPedido() {
        return new Pedido(
                getId(),
                getUsuario(),
                getProducto(),
                getPago(),
                getDireccion(),
                getStatus()
        );
    }
}
