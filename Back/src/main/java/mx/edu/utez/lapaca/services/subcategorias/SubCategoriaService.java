package mx.edu.utez.lapaca.services.subcategorias;

import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoriaRepository;
import mx.edu.utez.lapaca.services.logs.LogService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubCategoriaService {


    private final SubCategoriaRepository repository;

    private final LogService logService;
    private static final String SUBCATEGORIAS_CONSTANT = "Subcategorias";
    public SubCategoriaService(SubCategoriaRepository repository, LogService logService) {
        this.repository = repository;
        this.logService = logService;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<SubCategoria> insert(SubCategoria subCategoria) {
        Optional<SubCategoria> exists = repository.findByNombre(subCategoria.getNombre());
        try {
            if (exists.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Error... Subcategoria ya existente"
                );
            }
            SubCategoria savedSubCategoria = repository.save(subCategoria);
            logService.log("Insert", "SubCategoria registrada",SUBCATEGORIAS_CONSTANT);
            return new CustomResponse<>(
                    savedSubCategoria,
                    false,
                    200,
                    "Subcategoria registrada"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al registrar la subcategoria"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para insertar una subcategoria ilegal" + e.getMessage()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<SubCategoria>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<SubCategoria> getOne(Long id) {
        Optional<SubCategoria> subCategoria = repository.findById(id);
        try {
            if (subCategoria.isPresent()) {
                logService.log("Get", "SubCategoria con el ID " + id + " encontrada",
                        SUBCATEGORIAS_CONSTANT);
                return new CustomResponse<>(
                        subCategoria.get(),
                        false,
                        200,
                        "Subcategoria con el id " + subCategoria.get().getId() + " encontrada"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "La subcategoria con el id " + id + " no existe"
                );
            }
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al buscar la subategoria solicitada"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para obtener una subcategoria ilegal" + e.getMessage()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<SubCategoria> update(SubCategoria subCategoria) {
        try {
            if (!this.repository.existsById(subCategoria.getId())) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "La subcategoria no existe"
                );
            }
            SubCategoria savedSubCategoria = repository.save(subCategoria);
            logService.log("Update", "SubCategoria actualizada",SUBCATEGORIAS_CONSTANT);
            return new CustomResponse<>(
                    savedSubCategoria,
                    false,
                    200,
                    "Subcategoria actualizada"
            );

        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al actualizar la subcategoria"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para actualizar una subcategoria ilegal" + e.getMessage()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<SubCategoria> deleteById(Long id) {
        try {
            Optional<SubCategoria> subCategoriaId = repository.findById(id);
            if (!subCategoriaId.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "La csubcategoria con el id " + id + " no existe"
                );
            }
            SubCategoria subCategoria = subCategoriaId.get();
            repository.delete(subCategoria);
            logService.log("Delete", "SubCategoria eliminada: "+id,SUBCATEGORIAS_CONSTANT);
            return new CustomResponse<>(
                    null,
                    false,
                    200,
                    "La subcategoria con el id " + id + " ha sido eliminada correctamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al eliminar la subcategoria"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para eliminar una subcategoria ilegal" + e.getMessage()
            );
        }
    }

}

