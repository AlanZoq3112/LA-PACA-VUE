package mx.edu.utez.lapaca.controllers.usuarios;


import jakarta.validation.Valid;

import mx.edu.utez.lapaca.dto.usuarios.UsuarioDto;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.services.usuarios.UsuarioService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/usuario")
@CrossOrigin(origins = {"*"})
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //insert
    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Usuario>> insert(@Valid @RequestBody UsuarioDto usuarioDto){
        //Coloque que fuera user por defecto
        usuarioDto.setRole(Role.COMPRADOR);
        //Se encripta la contraseña
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
    @GetMapping("/getOne/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_COMPADOR')")
    public ResponseEntity<CustomResponse<Usuario>> getOne(@PathVariable Long id){
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Usuario>> update(@Valid @RequestBody UsuarioDto usuarioDto){
        //Se encripta la contraseña actualizada
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
