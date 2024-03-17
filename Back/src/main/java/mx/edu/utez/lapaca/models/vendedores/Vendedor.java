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

    @Column(nullable = false, length = 18)
    private String curp;

    @Column(name = "telefono", nullable = false, precision = 10)
    private Long telefonoVendedor;

    @Column(name = "ine", nullable = false)
    private String ine;


    @Column(nullable = false, length = 13)
    private String rfc;


    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;



}
