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

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagos_id", nullable = false)
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;

    @Column(nullable = false)
    private Boolean status;



}
