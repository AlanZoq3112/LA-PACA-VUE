package mx.edu.utez.lapaca.models.carritos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.carritoProductos.CarritoProducto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@Entity
@Table(name = "carritos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    //un/muchos carrito pertenece a un único usuario

    @ManyToOne
    @JoinColumn(name = "carrito_producto_id", nullable = false)
    private CarritoProducto carritoProducto;
    // Un producto en el carrito pertenece a un solo carrito


}
