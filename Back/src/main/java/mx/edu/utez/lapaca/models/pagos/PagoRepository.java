package mx.edu.utez.lapaca.models.pagos;

import mx.edu.utez.lapaca.models.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    Optional<Pago> findByNumero (String numero);


    List<Pago> findByUsuario(Usuario usuario);
}
