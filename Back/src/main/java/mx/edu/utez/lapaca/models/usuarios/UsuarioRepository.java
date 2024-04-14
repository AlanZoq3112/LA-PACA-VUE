package mx.edu.utez.lapaca.models.usuarios;

import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.productos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);




}
