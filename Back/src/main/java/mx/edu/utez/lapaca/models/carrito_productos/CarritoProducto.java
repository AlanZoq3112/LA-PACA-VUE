package mx.edu.utez.lapaca.models.carrito_productos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.productos.Producto;

@Entity
@Table(name = "carrito_productos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CarritoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cantidad;

    @OneToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    //un carrito_producto puede solo tener un producto asociado

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;
    // Un producto en el carrito pertenece a un solo carrito
}
