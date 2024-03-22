package mx.edu.utez.lapaca.controllers.productos;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.productos.ProductoDto;
import mx.edu.utez.lapaca.dto.usuarios.UsuarioDto;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.services.productos.ProductoService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-carsi-shop/producto")
@CrossOrigin(origins = {"*"})
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_COMPADOR')")
    public ResponseEntity<CustomResponse<Producto>> insert(@Valid @RequestBody ProductoDto productoDto){
        return new ResponseEntity<>(
                this.service.insert(productoDto.getProducto()),
                HttpStatus.CREATED
        );
    }



}
