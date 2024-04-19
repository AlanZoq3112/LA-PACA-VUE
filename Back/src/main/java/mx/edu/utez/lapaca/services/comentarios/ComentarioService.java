package mx.edu.utez.lapaca.services.comentarios;

import mx.edu.utez.lapaca.dto.comentarios.exceptions.CarritoNoPerteneAlUsuarioException;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.carritos.CarritoRepository;
import mx.edu.utez.lapaca.models.comentarios.Comentario;
import mx.edu.utez.lapaca.models.comentarios.ComentarioRepository;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.usuarios.UsuarioRepository;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ComentarioService {

    private final UsuarioRepository usuarioRepository;
    private final CarritoRepository carritoRepository;
    private final ComentarioRepository comentarioRepository;


    public ComentarioService(UsuarioRepository usuarioRepository, CarritoRepository carritoRepository, ComentarioRepository comentarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
        this.comentarioRepository = comentarioRepository;
    }

    public CustomResponse<Comentario> agregarComentario(Comentario comentario) {
        // Obtiene el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);

        // Verifica si el usuario autenticado existe
        if (!usuarioOptional.isPresent()) {
            return new CustomResponse<>(null, true, 404, "Usuario autenticado no encontrado");
        }

        Usuario usuarioAutenticado = usuarioOptional.get();

        // Establece el usuario en el comentario
        comentario.setUsuario(usuarioAutenticado);

        // Verifica que el carrito se estableció correctamente
        Producto producto = comentario.getProducto();
        if (producto == null) {
            return new CustomResponse<>(null, true, 400, "Producto no proporcionado en el comentario");
        }



        // **Validación del puntaje**
        int puntaje = comentario.getPuntaje();
        if (puntaje < 1 || puntaje > 5) {
            return new CustomResponse<>(null, true, 400, "El puntaje debe estar entre 1 y 5.");
        }

        // Guarda el comentario en la base de datos
        comentarioRepository.save(comentario);


        return new CustomResponse<>(comentario, false, 201, "Comentario agregado con éxito");
    }




}
