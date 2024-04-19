package mx.edu.utez.lapaca.dto.ofertas;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.ofertas.Oferta;
import mx.edu.utez.lapaca.models.productos.Producto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfertaDto {

    private Long id;

    @NotNull(message = "El estado no puede ser nulo")
    private boolean estado;

    @NotNull(message = "La fecha de inicio de la oferta es obligatoria")
    private Date fechaInicio;


    @NotNull(message = "La fecha de fin de la oferta es obligatoria")
    private Date fechaFin;

    @NotNull(message = "El porcentaje de la oferta es obligatoria")
    private double porcentajeDescuento;

    @NotNull(message = "La descripción de la oferta es obligatoria")
    private String descripcion;

    @NotNull(message = "El o los productos es obligatorio")
    private List<Producto> productos = new ArrayList<>();

    public Oferta getOferta() {
        Oferta oferta = new Oferta();
        oferta.setId(this.id);
        oferta.setEstado(this.estado);
        oferta.setFechaInicio(this.fechaInicio);
        oferta.setFechaFin(this.fechaFin);
        oferta.setPorcentajeDescuento(this.porcentajeDescuento);
        oferta.setDescripcion(this.descripcion);
        oferta.setProductos(this.productos);
        return oferta;
    }

}
