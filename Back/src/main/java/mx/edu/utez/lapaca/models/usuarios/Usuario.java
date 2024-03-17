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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String nombre;

    @Column(nullable = false, length = 15)
    private String genero;

    @Column(nullable = false, length = 200)
    private String imagenUrl;

    @Column(nullable = false, length = 35, unique = true)
    private String email;

    @Column(nullable = false, length = 65)
    private String password;

    @Column(name = "telefono", nullable = false, precision = 10)
    private Long telefono;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE) // solo guarda año/mes/dia yyyy-mm-dd
    private Date fechaNacimiento;



    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void getFechaNacimiento(Date fechaNacimiento) {
    }
}
