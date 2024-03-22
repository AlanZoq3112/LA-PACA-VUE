package mx.edu.utez.lapaca.services.productos;


import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.productos.ProductoRepository;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    private ProductoRepository repository;

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Producto> insert(Producto producto) {
        try {
            Producto savedProducto = repository.save(producto);
            return new CustomResponse<>(
                    savedProducto,
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


}
