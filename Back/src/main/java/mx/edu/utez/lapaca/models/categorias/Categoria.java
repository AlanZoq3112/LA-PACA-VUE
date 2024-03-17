package mx.edu.utez.lapaca.models.categorias;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;

import java.util.ArrayList;
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

    @Column(nullable = false, length = 10)
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //las subcategorías asociadas a una categoría eliminada también se eliminen de la base de datos
    @JoinColumn(name = "categoria_id", nullable = false)
    private List<SubCategoria> subcategorias;
    //una categoria puede tener asociadas muchas subcategorias
}


