package mx.edu.utez.lapaca.dto.carritoProductos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.carritoProductos.CarritoProducto;
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

        
    public CarritoProducto getCarritos() {
        return new CarritoProducto(
                getId(),
                getCantidad(),
                getProducto()
        );
    }

}
