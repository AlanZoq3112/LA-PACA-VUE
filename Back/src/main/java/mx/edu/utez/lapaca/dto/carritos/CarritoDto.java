package mx.edu.utez.lapaca.dto.carritos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.carritos.EstadoPedido;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.item_carrito.ItemCarrito;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoDto {

    private Long id;

    private List<ItemCarrito> items;

    private Double monto;

    private Usuario usuario;

    private Pago pago;

    private Direccion direccion;

    private EstadoPedido estado;

    private String idPago;


    public Carrito getCarrito () {
        return new Carrito(
                getId(),
                getItems(),
                getMonto(),
                getUsuario(),
                getPago(),
                getDireccion(),
                getEstado(),
                getIdPago()
        );
    }
}



