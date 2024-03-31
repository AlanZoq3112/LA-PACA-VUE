package mx.edu.utez.lapaca.dto.cantidad_pagos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.cantidad_pagos.CantidadPago;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CantidadPagoDto {

    private Long id;
    private Double monto;
    private Producto producto;
    private Pago pago;
    private Usuario usuario;

    public CantidadPago getCantidadPago() {
        return new CantidadPago(
                getId(),
                getMonto(),
                getProducto(),
                getPago(),
                getUsuario()
        );
    }

}
