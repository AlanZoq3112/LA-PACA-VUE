package mx.edu.utez.lapaca.models.direcciones;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@Entity
@Table(name = "direcciones")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String calle;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false, length = 25)
    private String colonia;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String municipio;

    @Column(nullable = false, precision = 5)
    private int codigoPostal;

    @Column(nullable = false, length = 100)
    private String referencia;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // un usuario puede tener muchas direcciones Y una dirección pertenece a un único usuario
}
