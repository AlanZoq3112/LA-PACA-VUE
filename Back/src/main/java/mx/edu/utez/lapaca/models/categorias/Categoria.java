package mx.edu.utez.lapaca.models.categorias;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;

import java.util.List;

@Entity
@Table(name = "categorias")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "categoria") //(cascade = CascadeType.ALL, orphanRemoval = true) | las subcategorías asociadas a una categoría eliminada también se eliminen de la base de datos
    @JsonIgnore
    private List<SubCategoria> subcategorias;

}


