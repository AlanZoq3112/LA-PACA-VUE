package mx.edu.utez.lapaca.controllers.categorias;

import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.categorias.CategoriaDto;
import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.services.categorias.CategoriaService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/categoria")
@CrossOrigin(origins = {"http://localhost:8091", "http://localhost:8080"})
public class CategoriaController {
    private final CategoriaService service;
    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Categoria>> insert(@Valid @RequestBody CategoriaDto categoriaDto){
        return new ResponseEntity<>(
                this.service.insert(categoriaDto.getCategoria()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<List<Categoria>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getOne")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Categoria>> getOne(@Valid @RequestBody Map<String, Long> requestBody){
        Long userId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.getOne(userId),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Categoria>> update(@Valid @RequestBody CategoriaDto categoriaDto){
        return new ResponseEntity<>(
                this.service.update(categoriaDto.getCategoria()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Categoria>> delete(@Valid @RequestBody Map<String, Long> requestBody){
        Long userId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.deleteById(userId),
                HttpStatus.OK
        );
    }

}
