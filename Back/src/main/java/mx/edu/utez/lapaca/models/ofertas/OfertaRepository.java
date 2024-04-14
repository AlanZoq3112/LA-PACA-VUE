package mx.edu.utez.lapaca.models.ofertas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    List<Oferta> findAllByEstadoTrue();

    @Query("SELECT o FROM Oferta o JOIN o.productos p WHERE p.id = :productoId AND o.estado = true")
    List<Oferta> findActiveOffersByProductId(Long productoId);

    @Query("SELECT DISTINCT o FROM Oferta o LEFT JOIN FETCH o.productos")
    List<Oferta> findAllWithProductos();
}
