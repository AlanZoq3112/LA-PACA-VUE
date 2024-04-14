package mx.edu.utez.lapaca.models.ofertas;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import mx.edu.utez.lapaca.models.productos.Producto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ofertas")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean estado;

    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private Date fechaFin;

    @Column(nullable = false)
    private double porcentajeDescuento; // Porcentaje de descuento

    @Column(nullable = false)
    private String descripcion; // Descripción de la oferta

    @ManyToMany
    @JoinTable(
            name = "oferta_producto",
            joinColumns = @JoinColumn(name = "oferta_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    //@JsonBackReference
    @JsonIgnoreProperties("ofertas")
    private List<Producto> productos = new ArrayList<>();

}
