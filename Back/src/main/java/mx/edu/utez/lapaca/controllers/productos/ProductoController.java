package mx.edu.utez.lapaca.controllers.productos;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.lapaca.dto.productos.ProductoDto;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.services.productos.ProductoService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/producto")
@CrossOrigin(origins = {"*"})
public class ProductoController {
    private final ProductoService service;
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Producto>> insert(@Valid @RequestBody ProductoDto productoDto){
        return new ResponseEntity<>(
                this.service.insert(productoDto.getProducto()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse<List<Producto>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getOne")
    public ResponseEntity<CustomResponse<Producto>> getOne(@Valid @RequestBody Map<String, Long> requestBody){
        Long productoId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.getOne(productoId),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Producto>> update(@Valid @RequestBody ProductoDto productoDto){
        return new ResponseEntity<>(
                this.service.update(productoDto.getProducto()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Producto>> delete(@Valid @RequestBody Map<String, Long> requestBody){
        Long productoId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.delete(productoId),
                HttpStatus.OK
        );
    }

}
