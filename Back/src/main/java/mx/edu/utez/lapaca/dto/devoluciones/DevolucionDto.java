package mx.edu.utez.lapaca.dto.devoluciones;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.devoluciones.Devolucion;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DevolucionDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "El estado de la devolución es obligatorio")
    private Boolean status;

    @NotBlank(message = "Este campo de comentario no puede ir vacio")
    @Size(max = 150, message = "El comentario debe tener como máximo {max} caracteres")
    private String comentario;



    public Devolucion getDevolucion() {
        return new Devolucion(
                getId(),
                getStatus(),
                getComentario()
        );
    }

}