package mx.edu.utez.lapaca.models.comentarios;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.pedidos.Pedido;
import mx.edu.utez.lapaca.models.productos.Producto;

@Entity
@Table(name = "comentarios")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(150)", nullable = false)
    private String comentarioTexto;

    @Column(nullable = false, length = 150)
    private int puntaje;


    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;


    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedidos;




}
