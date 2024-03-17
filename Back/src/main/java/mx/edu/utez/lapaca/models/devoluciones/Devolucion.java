package mx.edu.utez.lapaca.models.devoluciones;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.pedidos.Pedido;


@Entity
@Table(name = "devoluciones")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;


}
