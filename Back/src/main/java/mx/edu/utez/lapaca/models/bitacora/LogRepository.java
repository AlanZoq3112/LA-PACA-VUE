package mx.edu.utez.lapaca.models.bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAll();
}