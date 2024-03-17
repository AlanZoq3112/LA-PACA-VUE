package mx.edu.utez.lapaca.models.pagos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPago tipo;

    @Column(nullable = false)
    @Check(constraints = "numero BETWEEN 13 AND 19")
    private Long numero;

    @Column(nullable = false)
    private Integer cvv;

    @Column(nullable = false, length = 50)
    private String titular;

    @Column(nullable = false, length = 5)
    private String fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}
