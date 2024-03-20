package mx.edu.utez.lapaca.models.subcategorias;

import mx.edu.utez.lapaca.models.categorias.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Long> {

    Optional<SubCategoria> findByNombre(String nombre);
}
