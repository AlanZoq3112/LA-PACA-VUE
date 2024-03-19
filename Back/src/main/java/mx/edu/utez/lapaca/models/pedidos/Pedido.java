package mx.edu.utez.lapaca.models.pedidos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

import java.util.List;

@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    //muchos usuarios pueden realizar un pedido, y un pedido puede ser realizado por muchos productos
    //muchos pedidos pueden ser realizados por un usuario, y un usuario puede realizar muchos pedidos

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    //muchos pedidos pueden estar en un prodcuto, y un producto puede estar en muchos pedidos

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagos_id", nullable = false)
    private Pago pago;
    //un pedido solo puede ser pagado por una forma de pago

    @ManyToOne
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;

    @Column(nullable = false)
    private Boolean status;



}
