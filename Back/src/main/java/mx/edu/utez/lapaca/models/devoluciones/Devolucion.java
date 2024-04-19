package mx.edu.utez.lapaca.models.devoluciones;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "devoluciones")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private String comentario;



}
