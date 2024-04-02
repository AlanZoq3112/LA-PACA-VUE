package mx.edu.utez.lapaca.models.pagos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
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
    private String numero;

    @Column(nullable = false)
    private String cvv;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String titular;

    @Column(columnDefinition = "VARCHAR(5)", nullable = false)
    private String fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
