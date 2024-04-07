package mx.edu.utez.lapaca.models.productos;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.ofertas.Oferta;
import mx.edu.utez.lapaca.models.productosImagenes.ProductoImagen;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductoImagen> imagenes = new ArrayList<>();

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "subcategoria_id")
    private SubCategoria subCategoria;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    private List<Oferta> ofertas;


}
