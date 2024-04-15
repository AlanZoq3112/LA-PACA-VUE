package mx.edu.utez.lapaca.models.carritos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.itemCarrito.ItemCarrito;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("carrito")
    private List<ItemCarrito> items = new ArrayList<>();

    private Double monto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("carritos")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pago_id")
    @JsonIgnoreProperties("carritos")
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "direccion_id")
    @JsonIgnoreProperties("carritos")
    private Direccion direccion;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    private String Idpago;

}
