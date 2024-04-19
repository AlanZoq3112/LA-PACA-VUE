package mx.edu.utez.lapaca.controllers.comentarios;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.carritos.CarritoRepository;
import mx.edu.utez.lapaca.models.comentarios.Comentario;
import mx.edu.utez.lapaca.models.comentarios.ComentarioRepository;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.services.comentarios.ComentarioService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-carsi-shop/comentarios")
@CrossOrigin(origins = {"http://localhost:8091", "http://localhost:8080", "http://localhost:5173"})
public class ComentarioController {


    private final ComentarioService service;
    private final ComentarioRepository comentarioRepository;
    private final CarritoRepository carritoRepository;

    public ComentarioController(ComentarioService service, ComentarioRepository comentarioRepository, CarritoRepository carritoRepository) {
        this.service = service;
        this.comentarioRepository = comentarioRepository;
        this.carritoRepository = carritoRepository;
    }


    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_COMPRADOR')")
    public ResponseEntity<CustomResponse<Comentario>> agregarComentario(@Valid @RequestBody Comentario comentario) {
        // Llama al método agregarComentario en el servicio
        CustomResponse<Comentario> customResponse = service.agregarComentario(comentario);

        // Responde con el CustomResponse y el código de estado HTTP correspondiente
        if (customResponse.isError()) {
            return new ResponseEntity<>(customResponse, HttpStatus.resolve(customResponse.getStatusCode()));
        }

        return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
    }


    // Método para obtener comentarios de carritos comprados por el usuario autenticado
    @GetMapping("/carrito")
    public ResponseEntity<List<Comentario>> obtenerComentariosPorCarrito(@AuthenticationPrincipal Usuario usuarioAutenticado) {
        // Buscar carritos comprados por el usuario autenticado
        List<Carrito> carritos = carritoRepository.findByUsuarioId(usuarioAutenticado.getId());

        if (carritos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Buscar todos los comentarios asociados a los carritos encontrados
        List<Comentario> comentarios = comentarioRepository.findById((Carrito) carritos);

        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    // Método para obtener comentarios del usuario autenticado
    @GetMapping("/usuario")
    public ResponseEntity<List<Comentario>> obtenerComentariosPorUsuario(@AuthenticationPrincipal Usuario usuarioAutenticado) {
        List<Comentario> comentarios = comentarioRepository.findByUsuarioId(usuarioAutenticado.getId());

        if (comentarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

}
