package mx.edu.utez.lapaca.services.logs;

import mx.edu.utez.lapaca.models.bitacora.Log;
import mx.edu.utez.lapaca.services.usuarios.UsuarioService;
import mx.edu.utez.lapaca.models.bitacora.LogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void log(String operation, String description, String table) {
        Log log = new Log();
        log.setOperacion(operation);
        log.setDescripcion(description);
        log.setFechaHora(LocalDateTime.now());
        log.setTabla(table);
        logRepository.save(log);
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }
}