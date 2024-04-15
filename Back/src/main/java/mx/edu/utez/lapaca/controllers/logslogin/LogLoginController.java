package mx.edu.utez.lapaca.controllers.logslogin;

import lombok.AllArgsConstructor;
import mx.edu.utez.lapaca.models.bitacoralogin.LogLogin;
import mx.edu.utez.lapaca.services.logslogin.LogLoginService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/api-carsi-shop/loglogin")
@CrossOrigin(origins = {"http://localhost:8091", "http://localhost:8080", "http://localhost:5173"})
@AllArgsConstructor
public class LogLoginController {
    private final LogLoginService logLoginService;

    @GetMapping("/getAllLogslogin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<LogLogin> getLogsLogin(){
        return this.logLoginService.getAllLogslogin();
    }
}
