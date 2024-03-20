package mx.edu.utez.lapaca.controllers.subcategorias;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.categorias.CategoriaDto;
import mx.edu.utez.lapaca.dto.subcategorias.SubCategoriaDto;
import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;
import mx.edu.utez.lapaca.services.categorias.CategoriaService;
import mx.edu.utez.lapaca.services.subcategorias.SubCategoriaService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/admin/subcategoria")
@CrossOrigin(origins = {"*"})
public class SubCategoriaController {


    @Autowired
    SubCategoriaService service;

    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<SubCategoria>> insert(@Valid @RequestBody SubCategoriaDto subCategoriaDto){
        return new ResponseEntity<>(
                this.service.insert(subCategoriaDto.getSubCategoria()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<List<SubCategoria>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getOne")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<SubCategoria>> getOne(@Valid @RequestBody Map<String, Long> requestBody){
        Long userId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.getOne(userId),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<SubCategoria>> update(@Valid @RequestBody SubCategoriaDto subCategoriaDto){
        return new ResponseEntity<>(
                this.service.update(subCategoriaDto.getSubCategoria()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<SubCategoria>> delete(@Valid @RequestBody Map<String, Long> requestBody){
        Long userId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.deleteById(userId),
                HttpStatus.OK
        );
    }




}
