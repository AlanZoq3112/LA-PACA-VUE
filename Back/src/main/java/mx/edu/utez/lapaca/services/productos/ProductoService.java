package mx.edu.utez.lapaca.services.productos;


import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.productos.ProductoRepository;
import mx.edu.utez.lapaca.models.roles.Role;
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
public class ProductoService {


    private  final ProductoRepository repository;


    private final UsuarioRepository usuarioRepository;

    public ProductoService(ProductoRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }


    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Producto> insert(Producto producto) {
        try {
            // Obtener el usuario autenticado desde el contexto de Spring Security
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Obtener el nombre de usuario

            // Aquí puedes recuperar el usuario de tu base de datos usando el nombre de usuario o cualquier otro identificador
            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

            // Asignar el usuario al producto
            producto.setUsuario(usuario.get());

            // Establecer el estado del producto dependiendo del rol del usuario
            if (usuario.get().getRole() == Role.ADMIN) {
                producto.setEstado(true); // Si es admin, el producto se inserta como activo
            } else {
                producto.setEstado(false); // Si es vendedor u otro rol, el producto se inserta como pendiente
            }

            // Verificar si el producto ya existe
            Optional<Producto> exists = repository.findByNombre(producto.getNombre());
            if (exists.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Error... Producto ya registrado"
                );
            }
            // Guardar el producto
            Producto savedProducto = repository.save(producto);
            return new CustomResponse<>(
                    savedProducto,
                    false,
                    200,
                    "Producto registrado exitosamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al registrar el producto"
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
    public CustomResponse<List<Producto>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Producto> getOne(Long id) {
        Optional<Producto> producto = repository.findById(id);
        try {
            if (producto.isPresent()) {
                return new CustomResponse<>(
                        producto.get(),
                        false,
                        200,
                        "Producto con el id " + producto.get().getId() + " encontrado"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "El producto con el id " + id + " no existe"
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
    public CustomResponse<Producto> update(Producto producto) {
        try {
            // obtener el usuario autenticado desde el contexto de Spring Security
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // obtener el nombre de usuario

            // Aquí puedes recuperar el usuario de tu base de datos usando el nombre de usuario o cualquier otro identificador
            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

            // Asignar el usuario al producto
            producto.setUsuario(usuario.get());




            // Establecer el estado del producto dependiendo del rol del usuario
            if (usuario.get().getRole() == Role.ADMIN) {
                producto.setEstado(true); // Si es admin, el producto se inserta como activo
            } else {
                producto.setEstado(false); // Si es vendedor u otro rol, el producto se inserta como pendiente
            }

            // Guardar el producto
            Producto savedProducto = repository.save(producto);
            return new CustomResponse<>(
                    savedProducto,
                    false,
                    200,
                    "Producto actualizado exitosamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al actualizar el producto"
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
    public CustomResponse<Producto> delete(Long id) {
        try {
            Optional<Producto> optionalProducto = repository.findById(id);
            if (optionalProducto.isPresent()) {
                Producto producto = optionalProducto.get();
                producto.setEstado(false); // Establecer el estado como inactivo
                repository.save(producto);
                return new CustomResponse<>(
                        null,
                        false,
                        200,
                        "Producto con " + id + " marcado como inactivo"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        404,
                        "Producto con id " + id +" no encontrado"
                );
            }
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al borrar el producto"
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


