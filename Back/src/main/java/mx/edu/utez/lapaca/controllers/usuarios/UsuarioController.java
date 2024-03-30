package mx.edu.utez.lapaca.controllers.usuarios;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.usuarios.UsuarioDto;
import mx.edu.utez.lapaca.dto.usuarios.email.EmailDTO;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.services.usuarios.UsuarioService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/usuario")
@CrossOrigin(origins = {"*"})
public class UsuarioController {

    private final UsuarioService service;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    //insert
    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Usuario>> insert(@Valid @RequestBody UsuarioDto usuarioDto){
        usuarioDto.setRole(Role.COMPRADOR);
        String password = usuarioDto.getPassword();
        usuarioDto.setPassword(passwordEncoder.encode(password));
        return new ResponseEntity<>(
                this.service.insert(usuarioDto.getUsuario()),
                HttpStatus.CREATED
        );
    }

    //get all
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<List<Usuario>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get one
    @PostMapping("/getOne")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_COMPRADOR')")
    public ResponseEntity<CustomResponse<Usuario>> getOne(@Valid @RequestBody EmailDTO emailDTO){
        String email = emailDTO.getEmail();
        return new ResponseEntity<>(
                this.service.getOne(email),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Usuario>> update(@Valid @RequestBody UsuarioDto usuarioDto){
        String password = usuarioDto.getPassword();
        usuarioDto.setPassword(passwordEncoder.encode(password));
        return new ResponseEntity<>(
                this.service.update(usuarioDto.getUsuario()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Usuario>> delete(@Valid @RequestBody Map<String, Long> requestBody){
        Long userId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.deleteById(userId),
                HttpStatus.OK
        );
    }
}
