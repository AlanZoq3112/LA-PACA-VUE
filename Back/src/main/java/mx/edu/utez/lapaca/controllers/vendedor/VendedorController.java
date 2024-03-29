package mx.edu.utez.lapaca.controllers.vendedor;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.usuarios.UsuarioDto;
import mx.edu.utez.lapaca.dto.usuarios.email.EmailDTO;
import mx.edu.utez.lapaca.dto.vendedores.VendedorDto;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.vendedores.Vendedor;
import mx.edu.utez.lapaca.services.vendedores.VendedorService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api-carsi-shop/vendedor")
@CrossOrigin(origins = {"*"})
public class VendedorController {

    private final VendedorService service;



    public VendedorController(VendedorService service) {
        this.service = service;
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR')")
    public ResponseEntity<CustomResponse<Vendedor>> insert(@Valid @RequestBody VendedorDto vendedorDto){
        return new ResponseEntity<>(
                this.service.insert(vendedorDto.getVendedor()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<List<Vendedor>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getOne")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Vendedor>> getOne(@Valid @RequestBody Map<String, String> requestBody){
        String curp = requestBody.get("curp");
        return new ResponseEntity<>(
                this.service.getOne(curp),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Vendedor>> update(@Valid @RequestBody VendedorDto vendedorDto){
        return new ResponseEntity<>(
                this.service.update(vendedorDto.getVendedor()),
                HttpStatus.OK
        );
    }


    @PutMapping("/aprobarSolicitudVendedor")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Vendedor>> updateVendedorStatus(@RequestBody Map<String, Object> requestBody) {
        if (!requestBody.containsKey("id") || !requestBody.containsKey("estado")) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "El cuerpo de la solicitud debe contener el ID del vendedor y el estado"
            ));
        }

        long id = Long.parseLong(requestBody.get("id").toString());
        boolean estadoAprobado = (boolean) requestBody.get("estado");

        CustomResponse<Vendedor> response = service.aprobarSolicitudVendedor(id, estadoAprobado);
        if (response.getError()) {
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }



}
