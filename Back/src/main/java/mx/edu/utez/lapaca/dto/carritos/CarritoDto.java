package mx.edu.utez.lapaca.dto.carritos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.carrito_productos.CarritoProducto;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoDto {

    private Long id;

    @NotNull(message = "El id de usuario no puede ser nulo")
    private Usuario usuario;

    @NotNull(message = "id de carrito_productos no puede ser nula")
    private CarritoProducto carritoProducto;


    public Carrito getCarrito () {
        return new Carrito(
                getId(),
                getUsuario(),
                getCarritoProducto()

        );
    }


}



