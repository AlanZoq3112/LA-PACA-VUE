package mx.edu.utez.lapaca.dto.devoluciones;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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



    public Devolucion getDevolucion() {
        return new Devolucion(
                getId(),
                getStatus()
        );
    }

}
