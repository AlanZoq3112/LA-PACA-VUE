package mx.edu.utez.lapaca.models.usuarios;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.roles.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
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

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String nombre;

    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String genero;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String imagenUrl;

    @Column(columnDefinition = "VARCHAR(35)", nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 65)
    private String password;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String telefono;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE) // solo guarda año/mes/dia yyyy-mm-dd
    private LocalDate fechaNacimiento;



    @Column(columnDefinition = "VARCHAR(8)")
    private String secretPass;



    @Column(nullable = false)
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

    public void setId(String id) {
    }
}
