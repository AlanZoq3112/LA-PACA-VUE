package mx.edu.utez.lapaca.services.usuarios;


import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.usuarios.UsuarioRepository;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;


    //insert
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Usuario> insert(Usuario usuario) {
        Optional<Usuario> exists = repository.findByEmail(usuario.getEmail());
        try {
            if (exists.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Error... Usuario con correo ya registrado"
                );
            }
            Usuario savedUser = repository.save(usuario);
            return new CustomResponse<>(
                    savedUser,
                    false,
                    200,
                    "Usuario registrado"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al registrar el usuario"
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

    //get all
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Usuario>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    //get one by id
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Usuario> getOne(String email) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        try {
            if (usuario.isPresent()) {
                return new CustomResponse<>(
                        usuario.get(),
                        false,
                        200,
                        "Usuario con el correo: " + email + " encontrado"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "El usuario con el email " + email + " no existe"
                );
            }
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al buscar el usuario solicitado"
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
    public CustomResponse<Usuario> update(Usuario usuario) {
        try {
            if (!this.repository.existsById(usuario.getId())) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "El usuario no existe"
                );
            }
            Usuario savedUser = repository.save(usuario);
            return new CustomResponse<>(
                    savedUser,
                    false,
                    200,
                    "Usuario actualizado"
            );

        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al actualizar el usuario"
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


    //delete
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Usuario> deleteById(Long id) {
        try {
            Optional<Usuario> usuarioId = repository.findById(id);
            if (!usuarioId.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "El usuario con el id " + id + " no existe"
                );
            }
            Usuario usuario = usuarioId.get();
            repository.delete(usuario);
            return new CustomResponse<>(
                    null,
                    false,
                    200,
                    "El usuario con el id " + id + " ha sido eliminado correctamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al eliminar el usuario"
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
