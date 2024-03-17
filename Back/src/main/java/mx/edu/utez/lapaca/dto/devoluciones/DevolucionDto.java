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
import mx.edu.utez.lapaca.models.pedidos.Pedido;


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

    @NotNull(message = "El pedido asociado a la devolución es obligatorio")
    private Pedido pedido; // Pedido asociado a la devolución
    public Devolucion getDevolucion() {
        return new Devolucion(
                getId(),
                getStatus(),
                getPedido()
        );
    }

}
