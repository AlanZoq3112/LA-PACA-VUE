package mx.edu.utez.lapaca.models.roles;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String nombre;

}
