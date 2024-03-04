package mx.edu.utez.lapaca.dto.carrito_productos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.carrito_productos.CarritoProducto;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.productos.Producto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoProductoDto {

    private Long id;

    @Positive(message = "La cantidad debe ser un número positivo")
    private int cantidad;

    @NotNull(message = "El id de producto no puede ser nulo")
    private Producto producto;

    @NotNull(message = "El id de producto no puede ser nulo")
    private Carrito carrito;
    public CarritoProducto getCarritos() {
        return new CarritoProducto(
                getId(),
                getCantidad(),
                getProducto(),
                getCarrito()
        );
    }

}
