package mx.edu.utez.lapaca.models.vendedores;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.productosImagenes.ProductoImagen;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.vendedorImagen.VendedorImagen;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendedores")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(18)", nullable = false)
    private String curp;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String telefonoVendedor;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<VendedorImagen> imagenes = new ArrayList<>();

    @Column(columnDefinition = "VARCHAR(13)", nullable = false)
    private String rfc;

    @Column(nullable = false)
    private boolean estado;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Usuario usuario;

}
