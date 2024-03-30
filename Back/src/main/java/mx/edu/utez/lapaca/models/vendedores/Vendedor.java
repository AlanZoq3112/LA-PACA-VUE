package mx.edu.utez.lapaca.models.vendedores;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@Entity
@Table(name = "vendedores")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(18)", nullable = false)
    private String curp;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String telefonoVendedor;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String ine;

    @Column(columnDefinition = "VARCHAR(13)", nullable = false)
    private String rfc;

    @Column(nullable = false)
    private boolean estado;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
