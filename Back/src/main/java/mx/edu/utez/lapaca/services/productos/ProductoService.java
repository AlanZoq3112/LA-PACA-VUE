package mx.edu.utez.lapaca.services.productos;


import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.productos.ProductoRepository;
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


    private final ProductoRepository repository;


    private final UsuarioRepository usuarioRepository;

    public ProductoService(ProductoRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }


    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Producto> insert(Producto producto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Obtener el nombre de usuario

            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

            producto.setUsuario(usuario.get());


            // verificar si el producto ya existe
            Optional<Producto> exists = repository.findByNombre(producto.getNombre());
            if (exists.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Error... Producto ya registrado"
                );
            }

            // se marca la solicitud como pendiente de aprobación osea false hasta que el acmi la apruebe o nop
            producto.setEstado(false);

            // Guardar el producto
            Producto savedProducto = repository.save(producto);
            return new CustomResponse<>(
                    savedProducto,
                    false,
                    200,
                    "Producto registrado exitosamente... En espera de aprobación"
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // obtener el nombre de usuario

            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

            producto.setUsuario(usuario.get());

            // verificar si el usuario existe en la base de datos
            Optional<Producto> existingProductoOptional = repository.findById(producto.getId());
            if (existingProductoOptional.isEmpty()) {
                return new CustomResponse<>(
                        null,
                        true,
                        HttpStatus.NOT_FOUND.value(),
                        "El producto no existe");
            }

            // se guarda la solicitud de producto
            producto.setEstado(true);
            // se guardar el producto
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
    public CustomResponse<Producto> aprobarSolicitudProducto(long id, boolean estado) {
        Optional<Producto> productoOptional = repository.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setEstado(estado);
            repository.save(producto);
            // se actualiza el rol del usuario asociado si se aprueba como vendedor
            if (estado) {
                Usuario usuario = producto.getUsuario();
                usuarioRepository.save(usuario);
            } else if (!estado) {
                return new CustomResponse<>(
                        producto,
                        true,
                        HttpStatus.OK.value(),
                        "Solicitud denegada correctamente"
                );
            }
            return new CustomResponse<>(
                    producto,
                    false,
                    HttpStatus.OK.value(),
                    "Solicitud aprobada correctamente"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.NOT_FOUND.value(),
                    "No se encontró el vendedor con el ID proporcionado"
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Producto> delete(Long id) {
        try {
            Optional<Producto> optionalProducto = repository.findById(id);
            if (optionalProducto.isPresent()) {
                Producto producto = optionalProducto.get();
                producto.setEstado(false); // establecer el estado como inactivo
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


