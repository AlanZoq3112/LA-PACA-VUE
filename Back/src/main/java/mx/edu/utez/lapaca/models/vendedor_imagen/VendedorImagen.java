package mx.edu.utez.lapaca.models.vendedor_imagen;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.vendedores.Vendedor;

@Entity
@Table(name = "vendedores_ine")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VendedorImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    @JsonBackReference
    private Vendedor vendedor;

    @Column(nullable = false)
    private String imageUrl;
}
