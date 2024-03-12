package mx.edu.utez.lapaca.models.productos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.pedidos.Pedido;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

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

    @Column(nullable = false, length = 40)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String imagen_url;

    @Column(nullable = false, length = 100)
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
    //muchos productos pueden estar asociados a un mismo usuario

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    //muchos productos pueden estar asociados a un misma categoria

    @ManyToOne
    @JoinColumn(name = "subcategoria_id")
    private SubCategoria subcategoria;
    //muchos productos pueden estar asociados a un misma subcategoria

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL) //todas las operaciones de persistencia realizadas en un usuario (por ejemplo, guardar, actualizar, eliminar) se propagarán automáticamente a todas las direcciones asociadas
    private List<Pedido> pedidos;
    //un producto puede estar asociados a muchos pedidos, muchos pedidos pueden estar asociados a un producto

    //muchos pedidos pueden estar en un prodcuto, y un producto puede estar en muchos pedidos


}
