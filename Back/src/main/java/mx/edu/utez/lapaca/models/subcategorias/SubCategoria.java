package mx.edu.utez.lapaca.models.subcategorias;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.categorias.Categoria;


@Entity
@Table(name = "subcategorias")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SubCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(25)", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

}
