package mx.edu.utez.lapaca.models.bitacoralogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface LogLoginRepository extends JpaRepository<LogLogin, Long>{
    List<LogLogin> findAll();
}
