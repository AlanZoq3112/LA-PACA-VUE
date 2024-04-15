package mx.edu.utez.lapaca.services.direcciones;


import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.direcciones.DireccionRepository;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.usuarios.UsuarioRepository;
import mx.edu.utez.lapaca.services.logs.LogService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DireccionService {

    private final DireccionRepository repository;

    private final UsuarioRepository usuarioRepository;
    private final LogService logService;
    private static final String DIRECCIONES_CONSTANT = "Direcciones";
    private static final String DIRECCION_NO_EXISTE_MENSAJE = "Dirección con el id ";

    public DireccionService(DireccionRepository repository, UsuarioRepository usuarioRepository, LogService logService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.logService = logService;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Direccion> insert(Direccion direccion) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Obtener el nombre de usuario
            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
            direccion.setUsuario(usuario.get());

            Direccion savedDireccion = repository.save(direccion);
            logService.log("Insert", "Dirección registrada", DIRECCIONES_CONSTANT);
            return new CustomResponse<>(
                    savedDireccion,
                    false,
                    200,
                    "Dirección registrada exitosamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al registrar la dirección"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para insertar una dirección ilegales" + e.getMessage()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Direccion>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Direccion> getOne(Long id) {
        Optional<Direccion> direccion = repository.findById(id);
        try {
            if (direccion.isPresent()) {
                logService.log("Get", "Se encontró la dirección con el id: "
                        + id, DIRECCIONES_CONSTANT);
                return new CustomResponse<>(
                        direccion.get(),
                        false,
                        200,
                        DIRECCION_NO_EXISTE_MENSAJE + direccion.get().getId() + " encontrada"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        DIRECCION_NO_EXISTE_MENSAJE + id + " no existe"
                );
            }
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al buscar la dirección solicitado"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para obtener una dirección ilegal" + e.getMessage()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Direccion> update(Direccion direccion) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
            direccion.setUsuario(usuario.get());

            Optional<Direccion> existingDireccionOptional = repository.findById(direccion.getId());
            if (existingDireccionOptional.isEmpty()) {
                return new CustomResponse<>(
                        null,
                        true,
                        HttpStatus.NOT_FOUND.value(),
                        "La dirección no existe");
            }

            Direccion savedDireccion = repository.save(direccion);
            logService.log("Update", "Dirección actualizada", DIRECCIONES_CONSTANT);
            return new CustomResponse<>(
                    savedDireccion,
                    false,
                    200,
                    "Dirección actualizada exitosamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al actualizar la dirección"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para actualizar una dirección ilegal" + e.getMessage()
            );
        }
    }


    @Transactional(rollbackFor = {SQLException.class})
        public CustomResponse<Direccion> deleteById(Long id) {
        try {
            Optional<Direccion> direccionId = repository.findById(id);
            if (!direccionId.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        DIRECCION_NO_EXISTE_MENSAJE + id + " no existe"
                );
            }
            Direccion direccion = direccionId.get();
            repository.delete(direccion);
            logService.log("Delete", "Dirección eliminada con el id: "
                    + id, DIRECCIONES_CONSTANT);

            return new CustomResponse<>(
                    null,
                    false,
                    200,
                    DIRECCION_NO_EXISTE_MENSAJE + id + " ha sido eliminado correctamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al eliminar la dirección"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para eliminar una dirección incorrectos" + e.getMessage()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Direccion>> getAllByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            List<Direccion> direcciones = usuario.getDirecciones();
            logService.log("Get", "Se ha solicitado ver las direcciones" +
                    "asociadas al usuario", DIRECCIONES_CONSTANT);

            return new CustomResponse<>(
                    direcciones,
                    false,
                    200,
                    "Ok"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Usuario no encontrado"
            );
        }
    }

}
