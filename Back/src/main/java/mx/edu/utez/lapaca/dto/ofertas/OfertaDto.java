package mx.edu.utez.lapaca.dto.ofertas;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.ofertas.Oferta;
import mx.edu.utez.lapaca.models.productos.Producto;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfertaDto {

    private Long id;

    @NotNull(message = "El estado no puede ser nulo")
    private boolean estado;

    @NotNull(message = "La fecha de inicio de la oferta es obligatoria")
    @Future(message = "La fecha de inicio debe ser posterior o igual a la actual")
    private Date fecha_inicio;

    @NotNull(message = "La fecha de inicio de la oferta es obligatoria")
    @Future(message = "La fecha de inicio debe ser posterior a la actual")
    private Date fecha_fin;

    @NotNull(message = "El id de producto no puede ser nulo")
    private Producto producto;

    public Oferta getOferta() {
        return new Oferta(
                getId(),
                getOferta().isEstado(),
                getFecha_inicio(),
                getFecha_fin(),
                getProducto()
        );
    }



}
