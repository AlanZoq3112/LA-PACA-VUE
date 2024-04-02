package mx.edu.utez.lapaca.models.ofertas;


import jakarta.persistence.*;
import lombok.*;
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
    private Date fechaInicio;

    @Column(nullable = false)
    private Date fechaFin;




}
