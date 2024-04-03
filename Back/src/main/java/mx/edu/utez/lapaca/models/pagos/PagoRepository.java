package mx.edu.utez.lapaca.models.pagos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    Optional<Pago> findByNumero (String numero);

}
