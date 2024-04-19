package mx.edu.utez.lapaca.models.comentarios;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"genero", "image", "email", "password", "telefono", "fechaNacimiento",
    "secretPass","role","vendedor","direcciones","enabled","username","authorities","accountNonLocked",
    "credentialsNonExpired","accountNonExpired"})
    private Usuario usuario;

}
