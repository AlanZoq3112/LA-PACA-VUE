package mx.edu.utez.lapaca.security.bitacora_login;

import jakarta.persistence.*;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora_login")
public class BitacoraLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String operacion;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}