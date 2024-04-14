package mx.edu.utez.lapaca.dto.comentarios;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.comentarios.Comentario;
import mx.edu.utez.lapaca.models.productos.Producto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComentarioDto {

    private Long id;

    @NotBlank(message = "Este campo es obligatorio")
    @Size(max = 150, message = "El comentario debe tener como máximo {max} caracteres")
    private String comentarioTexto;

    @Min(value = 0, message = "El puntaje mínimo es 0")
    @Max(value = 5L, message = "El puntaje máximo es 5")
    private int puntaje;

    @NotNull(message = "id de productos no puede ser nula")
    private Producto producto;




    public Comentario getComentario() {
        return new Comentario(
                getId(),
                getComentarioTexto(),
                getPuntaje(),
                getProducto()
        );
    }

}
