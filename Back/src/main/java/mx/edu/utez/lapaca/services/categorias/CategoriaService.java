package mx.edu.utez.lapaca.services.categorias;


import mx.edu.utez.lapaca.models.bitacora.Bitacora;
import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.categorias.CategoriaRepository;
import mx.edu.utez.lapaca.services.bitacora.BitacoraService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
@Transactional
public class CategoriaService {


    private final CategoriaRepository repository;

    private final BitacoraService bitacoraService;

    public CategoriaService(CategoriaRepository repository, BitacoraService bitacoraService) {
        this.repository = repository;
        this.bitacoraService = bitacoraService;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Categoria> insert(Categoria categoria) {
        Optional<Categoria> exists = repository.findByNombre(categoria.getNombre());
        try {
            if (exists.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Error... Categoria ya existente"
                );
            }
            Categoria savedUser = repository.save(categoria);
            Bitacora bitacora = new Bitacora();
            bitacora.setTabla("carsi_shop");
            bitacora.setDescripcion("Inserción");
            bitacora.setDescripcion("Nueva categoria creada: " + savedUser.getNombre());
            bitacora.setFechaHora(LocalDateTime.now());
            bitacoraService.registrarLog(bitacora);

            return new CustomResponse<>(
                    savedUser,
                    false,
                    200,
                    "Categoria registrada"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al registrar la categoria"
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
    public CustomResponse<List<Categoria>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Categoria> getOne(Long id) {
        Optional<Categoria> categoria = repository.findById(id);
        try {
            if (categoria.isPresent()) {
                return new CustomResponse<>(
                        categoria.get(),
                        false,
                        200,
                        "Categoria con el id " + categoria.get().getId() + " encontrada"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "La categoria con el id " + id + " no existe"
                );
            }
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al buscar la categoria solicitada"
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
    public CustomResponse<Categoria> update(Categoria categoria) {
        try {
            if (!this.repository.existsById(categoria.getId())) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "La categoria no existe"
                );
            }
            Categoria savedCategoria = repository.save(categoria);
            Bitacora bitacora = new Bitacora();
            bitacora.setTabla("carsi_shop");
            bitacora.setDescripcion("Actualización");
            bitacora.setDescripcion("Se actualizo la sigueinte categoria: " + savedCategoria.getNombre());
            bitacora.setFechaHora(LocalDateTime.now());
            bitacoraService.registrarLog(bitacora);

            return new CustomResponse<>(
                    savedCategoria,
                    false,
                    200,
                    "Categoria actualizada"
            );

        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al actualizar la categoria"
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
    public CustomResponse<Categoria> deleteById(Long id) {
        try {
            Optional<Categoria> categoriaId = repository.findById(id);
            if (!categoriaId.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "La categoria con el id " + id + " no existe"
                );
            }
            Categoria categoria = categoriaId.get();
            repository.delete(categoria);
            Bitacora bitacora = new Bitacora();
            bitacora.setTabla("carsi_shop");
            bitacora.setDescripcion("Eliminación");
            bitacora.setDescripcion("Se elimino la siguiente categoria: " + categoria);
            bitacora.setFechaHora(LocalDateTime.now());
            bitacoraService.registrarLog(bitacora);

            return new CustomResponse<>(
                    null,
                    false,
                    200,
                    "La categoria con el id " + id + " ha sido eliminado correctamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al eliminar la categoria"
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
