package mx.edu.utez.lapaca.services.direcciones;


import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.direcciones.DireccionRepository;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.usuarios.UsuarioRepository;
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

    public DireccionService(DireccionRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Direccion> insert(Direccion direccion) {
        try {
            // Obtener el usuario autenticado desde el contexto de Spring Security
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Obtener el nombre de usuario

            // Aquí puedes recuperar el usuario de tu base de datos usando el nombre de usuario o cualquier otro identificador
            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

            // Asignar la dirección al usuario
            direccion.setUsuario(usuario.get());

            // Guardar la dirección
            Direccion savedDireccion = repository.save(direccion);
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
                    "Error... argumento ilegal" + e.getMessage()
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
                return new CustomResponse<>(
                        direccion.get(),
                        false,
                        200,
                        "Dirección con el id " + direccion.get().getId() + " encontrada"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "La dirección con el id " + id + " no existe"
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
                    "Error... argumento ilegal" + e.getMessage()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Direccion> update(Direccion direccion) {
        try {
            // obtener el usuario autenticado desde el contexto de Spring Security
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // obtener el nombre de usuario

            // Aquí puedes recuperar el usuario de tu base de datos usando el nombre de usuario o cualquier otro identificador
            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

            // Asignar el usuario al producto
            direccion.setUsuario(usuario.get());

            // Verificar si el usuario existe en la base de datos
            Optional<Direccion> existingDireccionOptional = repository.findById(direccion.getId());
            if (existingDireccionOptional.isEmpty()) {
                return new CustomResponse<>(
                        null,
                        true,
                        HttpStatus.NOT_FOUND.value(),
                        "La dirección no existe");
            }

            // Guardar el producto
            Direccion savedDireccion = repository.save(direccion);
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
                    "Error... argumento ilegal" + e.getMessage()
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
                        "La dirección con el id " + id + " no existe"
                );
            }
            Direccion direccion = direccionId.get();
            repository.delete(direccion);
            return new CustomResponse<>(
                    null,
                    false,
                    200,
                    "La dirección con el id " + id + " ha sido eliminado correctamente"
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
                    "Error... argumento ilegal" + e.getMessage()
            );
        }
    }



}
