package mx.edu.utez.lapaca.services.productos;

import mx.edu.utez.lapaca.dto.productos.validators.ProductoAlreadyExistsException;
import mx.edu.utez.lapaca.dto.productos.validators.ProductoInsertException;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.productos.ProductoRepository;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.usuarios.UsuarioRepository;
import mx.edu.utez.lapaca.services.firebase.FirebaseService;
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
public class ProductoService {


    private final ProductoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final LogService logService;
    private static final String PRODUCTOS_CONSTANT = "Productos";
    private static final String APROBACION_TABLA_CONSTANT = "Aprobacion";

    public ProductoService(ProductoRepository repository, UsuarioRepository usuarioRepository, LogService logService) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.logService = logService;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Producto> insert(Producto producto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
            producto.setUsuario(usuario.get());
            Optional<Producto> exists = repository.findByNombre(producto.getNombre());
            if (exists.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Error... Producto ya registrado"
                );
            }
            producto.setEstado(1);
            Producto savedProducto = repository.save(producto);
            logService.log("Insert", "Producto Agregado", "Productos");
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
                    "Error... datos para insertar un producto ilegal" + e.getMessage()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                logService.log("GetOne", "Consulta del producto con el ID: " + id,
                        PRODUCTOS_CONSTANT);
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
                    "Error... datos para obtener un producto ilegal" + e.getMessage()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Producto> update(Producto producto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

            producto.setUsuario(usuario.get());

            Optional<Producto> existingProductoOptional = repository.findById(producto.getId());
            if (existingProductoOptional.isEmpty()) {
                return new CustomResponse<>(
                        null,
                        true,
                        HttpStatus.NOT_FOUND.value(),
                        "El producto no existe");
            }

            producto.setEstado(2);
            Producto savedProducto = repository.save(producto);
            logService.log("Update", "Producto Actualizado",PRODUCTOS_CONSTANT);
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
                    "Error... datos para actualizar un producto ilegal" + e.getMessage()
            );
        }
    }


    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Producto> aprobarSolicitudProducto(long id, int estado) {
        Optional<Producto> productoOptional = repository.findById(id);
        if (productoOptional.isPresent()) {

            Producto producto = productoOptional.get();

            if (estado != 0 && estado != 1 && estado != 2 && estado !=3) {
                return new CustomResponse<>(
                        null,
                        true,
                        HttpStatus.BAD_REQUEST.value(),
                        "Estado de producto no válido"
                );
            }
            producto.setEstado(estado);
            repository.save(producto);

            if (estado == 3) {
                Usuario usuario = producto.getUsuario();
                usuarioRepository.save(usuario);
            }
            String mensaje = "";
            if (estado == 3) {
                logService.log(APROBACION_TABLA_CONSTANT, "El Administrador aprobo el producto con el ID: " + id,PRODUCTOS_CONSTANT);
                mensaje = "Solicitud aprobada correctamente";
            } else if (estado == 0) {
                logService.log(APROBACION_TABLA_CONSTANT, "El Administrador marcó como inactivo el producto con el ID: " + id,PRODUCTOS_CONSTANT);
                mensaje = "Producto marcado como inactivo correctamente";
            } else if (estado == 1) {
                logService.log(APROBACION_TABLA_CONSTANT, "El Administrador marcó como pendiente el producto con el ID: " + id,PRODUCTOS_CONSTANT);
                mensaje = "Producto marcado como pendiente correctamente";
            }  else if (estado == 2) {
                logService.log(APROBACION_TABLA_CONSTANT, "El Administrador rechazó el producto con el ID: " + id,PRODUCTOS_CONSTANT);
                mensaje = "Producto rechazado correctamente";
            }
            return new CustomResponse<>(
                    producto,
                    false,
                    HttpStatus.OK.value(),
                    mensaje
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.NOT_FOUND.value(),
                    "No se encontró el producto con el ID proporcionado"
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Producto> delete(Long id) {
        try {
            Optional<Producto> optionalProducto = repository.findById(id);
            if (optionalProducto.isPresent()) {
                Producto producto = optionalProducto.get();
                producto.setEstado(0); // establecer el estado como inactivo
                repository.save(producto);
                logService.log("Delete", "Producto eliminado (inactivo) con el ID: " + id,PRODUCTOS_CONSTANT);
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
                    "Error... datos para eliminar un producto ilegal" + e.getMessage()
            );
        }
    }



    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Producto>> getAllByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            List<Producto> productos = repository.findByUsuario(usuario);
            logService.log("Get", "El usuario con el correo "
                    + usuario + "ha solicitado ver sus productos",PRODUCTOS_CONSTANT);
            return new CustomResponse<>(
                    productos,
                    false,
                    200,
                    "OK"
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

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Producto>> getAllApprovedProducts() {
        try {
            List<Producto> productos = repository.findByEstadoTrue();
            return new CustomResponse<>(
                    productos,
                    false,
                    200,
                    "Ok"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al obtener los productos aprobados"
            );
        }
    }

}