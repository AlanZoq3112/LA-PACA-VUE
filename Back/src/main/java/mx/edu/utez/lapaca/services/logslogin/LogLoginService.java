package mx.edu.utez.lapaca.services.logslogin;

import mx.edu.utez.lapaca.models.bitacoralogin.LogLogin;
import mx.edu.utez.lapaca.models.bitacoralogin.LogLoginRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogLoginService {
    private final LogLoginRepository logLoginRepository;
    public LogLoginService(LogLoginRepository logLoginRepository){
        this.logLoginRepository = logLoginRepository;
    }

    public LogLogin guardarLogLogin(String operacion, String descripcion, String tabla, String usuario){
        LogLogin logLogin = new LogLogin();
        logLogin.setOperacion(operacion);
        logLogin.setDescripcion(descripcion);
        logLogin.setTabla(tabla);
        logLogin.setFechaHora(LocalDateTime.now());
        logLogin.setUsuario(usuario);
        return logLoginRepository.save(logLogin);
    }
    public List<LogLogin> getAllLogslogin(){
        return logLoginRepository.findAll();
    }
}
