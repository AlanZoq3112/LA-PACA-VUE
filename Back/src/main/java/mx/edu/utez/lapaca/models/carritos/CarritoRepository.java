package mx.edu.utez.lapaca.models.carritos;


import mx.edu.utez.lapaca.models.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    List<Carrito> findByUsuario(Usuario usuario);

    @Modifying
    @Query("UPDATE Carrito c SET c.pago = NULL WHERE c.pago.id = :pagoId")
    void updatePagoToNullByPagoId(Long pagoId);




}
