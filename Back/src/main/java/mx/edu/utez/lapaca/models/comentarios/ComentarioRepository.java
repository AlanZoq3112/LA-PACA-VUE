package mx.edu.utez.lapaca.models.comentarios;

import mx.edu.utez.lapaca.models.carritos.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByUsuarioId(Long id);



    List<Comentario> findById(Carrito carritos);
}
