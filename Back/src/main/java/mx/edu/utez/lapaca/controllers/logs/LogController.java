package mx.edu.utez.lapaca.controllers.logs;

import lombok.AllArgsConstructor;
import mx.edu.utez.lapaca.models.bitacora.Log;
import mx.edu.utez.lapaca.services.logs.LogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/api-carsi-shop/log")
@CrossOrigin(origins = {"http://localhost:8091", "http://localhost:8080"})
@AllArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping("/getAllLogs")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Log> getLogs(){
        return this.logService.getAllLogs();
    }
}