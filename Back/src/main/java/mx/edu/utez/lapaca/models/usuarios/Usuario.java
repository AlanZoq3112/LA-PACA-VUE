package mx.edu.utez.lapaca.models.usuarios;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.roles.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String imagen_url;

    @Column(nullable = false, length = 35, unique = true)
    private String email;

    @Column(nullable = false, length = 35)
    private String contrasena;

    @Column(name = "telefono", nullable = false, precision = 10)
    private Long telefono;


    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role rol;
    // cada usuario pertenece a un rol, y cada rol puede tener varios usuarios asociados

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL) //todas las operaciones de persistencia realizadas en un usuario (por ejemplo, guardar, actualizar, eliminar) se propagarán automáticamente a todas las direcciones asociadas
    private List<Direccion> direcciones;
    // un usuario puede tener muchas direcciones Y una dirección pertenece a un único usuario


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Pago> pagos;

    //muchos pagos pueden estar asociados a un solo usuario (cada pago pertenece a un solo usuario),
    //y un usuario puede tener asociados muchos pagos

    //productos relacion? idkkkkk



    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL) //todas las operaciones de persistencia realizadas en un usuario (por ejemplo, guardar, actualizar, eliminar) se propagarán automáticamente a todas las direcciones asociadas
    private List<Producto> productos;
// un usuario puede tener muchas direcciones Y una dirección pertenece a un único usuario
}
