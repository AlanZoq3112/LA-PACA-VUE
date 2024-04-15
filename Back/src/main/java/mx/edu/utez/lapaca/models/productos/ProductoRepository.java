package mx.edu.utez.lapaca.models.productos;

import mx.edu.utez.lapaca.models.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByUsuario(Usuario usuario);

    List<Producto> findByEstado(int estado);

    @Query("SELECT DISTINCT p FROM Producto p LEFT JOIN FETCH p.ofertas")
    List<Producto> findAllWithOfertas();
}
