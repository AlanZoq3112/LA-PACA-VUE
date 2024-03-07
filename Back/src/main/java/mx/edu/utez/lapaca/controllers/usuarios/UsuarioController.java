package mx.edu.utez.lapaca.controllers.usuarios;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.roles.RoleDto;
import mx.edu.utez.lapaca.dto.usuarios.UsuarioDto;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.services.usuarios.UsuarioService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/usuario/")
@CrossOrigin(origins = {"*"})
public class UsuarioController {

    @Autowired
    UsuarioService service;

    //insert
    @PostMapping("/insert")
    public ResponseEntity<CustomResponse<Usuario>> insert(@Valid @RequestBody UsuarioDto usuarioDto){
        return new ResponseEntity<>(
                this.service.insert(usuarioDto.getUsuario()),
                HttpStatus.CREATED
        );
    }

    //get all
    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse<List<Usuario>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get one
    @GetMapping("/getOne")
    public ResponseEntity<CustomResponse<Usuario>> getOne(@Valid @RequestBody Map<String, Long> requestBody){
        Long userId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.getOne(userId),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse<Usuario>> update(@Valid @RequestBody UsuarioDto usuarioDto){
        return new ResponseEntity<>(
                this.service.update(usuarioDto.getUsuario()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse<Usuario>> delete(@PathVariable Long id){
        return new ResponseEntity<>(
                this.service.deleteById(id),
                HttpStatus.OK
        );
    }


}
