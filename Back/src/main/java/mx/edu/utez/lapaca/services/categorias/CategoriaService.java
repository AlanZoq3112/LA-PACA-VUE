package mx.edu.utez.lapaca.services.categorias;

import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.categorias.CategoriaRepository;
import mx.edu.utez.lapaca.services.cifrado.EncryptionService;
import mx.edu.utez.lapaca.services.logs.LogService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaService {


    private final CategoriaRepository repository;
    private final LogService logService;
    private static final String CATEGORIAS_CONSTANT = "Categorias";
    private static final String CATEGORIA_NO_EXISTE_MENSAJE = "La categoria con el id ";

    private final PasswordEncoder passwordEncoder;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")

    @Autowired
    private EncryptionService encryptionService;



    public CategoriaService(CategoriaRepository repository, LogService logService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.logService = logService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Categoria> insert(Categoria categoria) {
        Optional<Categoria> exists = repository.findByNombre(categoria.getNombre());
        logService.log("Insert", "Categoria registrada", "Categorias");
        try {
            if (exists.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Error... Categoria ya existente"
                );
            }
            String nombre = encryptionService.encrypt(categoria.getNombre());
            categoria.setNombre(nombre);
            Categoria savedUser = repository.save(categoria);
            logService.log("Insert", "Categoria registrada", CATEGORIAS_CONSTANT);
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
                    "Error... datos para insertar de categoria ilegal" + e.getMessage()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Categoria>> getAll() throws Exception {
        List<Categoria> categoriasEncriptado = this.repository.findAll();
        List<Categoria> categorias = new ArrayList<>();
        for (Categoria categoria : categoriasEncriptado) {
            Categoria categoriaNueva = new Categoria();
            String nombre = encryptionService.decrypt(categoria.getNombre());
            categoriaNueva.setNombre(nombre);
            categoriaNueva.setId(categoria.getId());
            categorias.add(categoriaNueva);
        }
        return new CustomResponse<>(
                categorias,
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Categoria> getOne(Long id) throws Exception {
        Optional<Categoria> categoria = repository.findById(id);
        try {
            if (categoria.isPresent()) {
                logService.log("GetOne", "Se encontro la categoria con id_:" + id, CATEGORIAS_CONSTANT);
                String nombre = encryptionService.decrypt(categoria.get().getNombre());
                Categoria categoria1 = new Categoria();
                categoria1.setNombre(nombre);
                categoria1.setId(id);
                System.out.println(categoria1.getNombre());
                return new CustomResponse<>(
                        categoria1,
                        false,
                        200,
                        CATEGORIA_NO_EXISTE_MENSAJE + categoria.get().getId() + " encontrada"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        CATEGORIA_NO_EXISTE_MENSAJE + id + " no existe"
                );
            }
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al buscar la categoria solicitada"
            );
        }
        catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para obtener una categoria ilegal" + e.getMessage()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            String nombre = encryptionService.encrypt(categoria.getNombre());
            categoria.setNombre(nombre);
            Categoria savedCategoria = repository.save(categoria);
            logService.log("Update", "Categoria Actualizada", CATEGORIAS_CONSTANT);
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
                    "Error... datos para actualizar una categoria ilegal" + e.getMessage()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                        CATEGORIA_NO_EXISTE_MENSAJE + id + " no existe"
                );
            }
            Categoria categoria = categoriaId.get();
            logService.log("Delete", "Categoria Eliminada con el ID: " + id,  CATEGORIAS_CONSTANT);
            repository.delete(categoria);
            return new CustomResponse<>(
                    null,
                    false,
                    200,
                    CATEGORIA_NO_EXISTE_MENSAJE + id + " ha sido eliminado correctamente"
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
                    "Error... datos para eliminar una categoria ilegal" + e.getMessage()
            );
        }
    }


}
