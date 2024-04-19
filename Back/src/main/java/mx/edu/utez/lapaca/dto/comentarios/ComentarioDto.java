package mx.edu.utez.lapaca.dto.comentarios;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.comentarios.Comentario;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComentarioDto {

    private Long id;

    @NotBlank(message = "Este campo de comentario no puede ir vacio")
    @Size(max = 200, message = "El comentario debe tener como máximo {max} caracteres")
    private String comentarioTexto;

    @NotBlank(message = "El puntaje no puede ser nulo")
    @Min(value = 0, message = "El puntaje mínimo es 0")
    @Max(value = 5L, message = "El puntaje máximo es 5")
    private int puntaje;

    private Producto producto;

    private Usuario usuario;

    public Comentario getComentario() {
        return new Comentario(
                getId(),
                getComentarioTexto(),
                getPuntaje(),
                getProducto(),
                getUsuario()
        );
    }

}
