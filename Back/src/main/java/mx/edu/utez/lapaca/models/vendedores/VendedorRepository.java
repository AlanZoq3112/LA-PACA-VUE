package mx.edu.utez.lapaca.models.vendedores;

import mx.edu.utez.lapaca.models.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Optional<Vendedor> findByUsuario(Usuario usuario);

    Optional<Vendedor> findByCurp(String curp);
}
