package mx.edu.utez.lapaca.controllers.direcciones;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.direcciones.DireccionDto;
import mx.edu.utez.lapaca.dto.productos.ProductoDto;
import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.services.direcciones.DireccionService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/direccion")
@CrossOrigin(origins = {"*"})
public class DireccionController {

    private final DireccionService service;

    public DireccionController(DireccionService service) {
        this.service = service;
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Direccion>> insert(@Valid @RequestBody DireccionDto direccionDto){
        return new ResponseEntity<>(
                this.service.insert(direccionDto.getDireccion()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<List<Direccion>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getOne")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Direccion>> getOne(@Valid @RequestBody Map<String, Long> requestBody){
        Long id = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Direccion>> update(@Valid @RequestBody DireccionDto direccionDto){
        return new ResponseEntity<>(
                this.service.update(direccionDto.getDireccion()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Direccion>> delete(@Valid @RequestBody Map<String, Long> requestBody){
        Long id = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.deleteById(id),
                HttpStatus.OK
        );
    }

}
