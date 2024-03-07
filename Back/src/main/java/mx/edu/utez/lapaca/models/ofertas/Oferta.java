package mx.edu.utez.lapaca.models.ofertas;


import jakarta.persistence.*;
import lombok.*;
import mx.edu.utez.lapaca.models.productos.Producto;

import java.util.Date;

@Entity
@Table(name = "ofertas")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean estado;

    @Column(nullable = false)
    private Date fecha_inicio;

    @Column(nullable = false)
    private Date fecha_fin;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    //una oferta está asociada con un único producto


}
