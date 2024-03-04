package mx.edu.utez.lapaca.models.carritos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.carrito_productos.CarritoProducto;
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

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    //un/muchos carrito pertenece a un único usuario

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarritoProducto> carrito_productos;
    //un carrito puede contener varios productos en forma de carrito_productos

}
