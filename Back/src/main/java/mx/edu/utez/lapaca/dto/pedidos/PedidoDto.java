package mx.edu.utez.lapaca.dto.pedidos;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.pedidos.Pedido;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

import java.util.List;

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

    public Pedido getPedido() {
        return new Pedido(
                getId(),
                getUsuario(),
                getProducto(),
                getPago()
        );
    }
}
