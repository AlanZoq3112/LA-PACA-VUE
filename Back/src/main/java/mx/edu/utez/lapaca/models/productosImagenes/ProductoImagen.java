package mx.edu.utez.lapaca.models.productosImagenes;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.productos.Producto;

@Entity
@Table(name = "productos_imagenes")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductoImagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonBackReference
    private Producto producto;

    @Column(nullable = false)
    private String imageUrl;

    // Otros campos y relaciones...

    // Getters y setters...
}