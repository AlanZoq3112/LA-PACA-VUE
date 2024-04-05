package mx.edu.utez.lapaca.controllers.productos;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.productos.ProductoDto;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.services.firebase.FirebaseService;
import mx.edu.utez.lapaca.services.productos.ProductoService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/producto")
@CrossOrigin(origins = {"*"})
public class ProductoController {
    private final ProductoService service;

    private final FirebaseService firebaseService;
    public ProductoController(ProductoService service, FirebaseService firebaseService) {
        this.service = service;
        this.firebaseService = firebaseService;
    }

    @PostMapping(value = "/insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Producto>> insert(@Valid @ModelAttribute ProductoDto productoDto) throws Exception {
        MultipartFile imageFile = productoDto.getImage();
        // Subir imagen a Firebase y obtener URL
        String imageUrl = firebaseService.uploadFile(imageFile);
        productoDto.setImage(imageFile);
        if (imageUrl == null) {
            throw new Exception("Failed to upload image to Firebase.");
        }
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

    @PutMapping("/aprobarSolicitudProducto")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Producto>> updateProductoStatus(@RequestBody Map<String, Object> requestBody) {
        if (!requestBody.containsKey("id") || !requestBody.containsKey("estado")) {
            return ResponseEntity.badRequest().body(new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "El cuerpo de la solicitud debe contener el ID del producto y el estado"
            ));
        }

        long id = Long.parseLong(requestBody.get("id").toString());
        boolean estadoAprobado = (boolean) requestBody.get("estado");

        CustomResponse<Producto> response = service.aprobarSolicitudProducto(id, estadoAprobado);
        if (response.getError()) {
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
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
