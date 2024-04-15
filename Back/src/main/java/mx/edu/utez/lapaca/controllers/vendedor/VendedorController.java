package mx.edu.utez.lapaca.controllers.vendedor;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.productos.validators.images.ImageCountException;
import mx.edu.utez.lapaca.dto.productos.validators.images.ImageSizeException;
import mx.edu.utez.lapaca.dto.productos.validators.images.ImageUploadException;
import mx.edu.utez.lapaca.dto.vendedores.VendedorDto;
import mx.edu.utez.lapaca.models.vendedor_imagen.VendedorImagen;
import mx.edu.utez.lapaca.models.vendedores.Vendedor;
import mx.edu.utez.lapaca.services.firebase.FirebaseService;
import mx.edu.utez.lapaca.services.vendedores.VendedorService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/vendedor")
@CrossOrigin(origins = {"http://localhost:8091", "http://localhost:8080"})
public class VendedorController {

    private final VendedorService service;
    private final FirebaseService firebaseService;

    public VendedorController(VendedorService service, FirebaseService firebaseService) {
        this.service = service;
        this.firebaseService = firebaseService;
    }

    @PostMapping(value="/insert",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR')")
    public ResponseEntity<CustomResponse<Vendedor>> insert(@Valid @ModelAttribute VendedorDto vendedorDto) throws Exception {
        List<MultipartFile> imageFiles = vendedorDto.getImagenes();
        if (imageFiles.size() != 2) {
            throw new ImageCountException("Debe proporcionar su INE por los dos lados");
        }
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            if (imageFile.getSize() > 2 * 1024 * 1024) {
                throw new ImageSizeException("El tamaño de una imagen excede el límite de 2MB.");
            }
            String imageUrl = firebaseService.uploadFileVendedor(imageFile);
            if (imageUrl == null) {
                throw new ImageUploadException("Error al subir una imagen a Firebase.");
            }
            imageUrls.add(imageUrl);
        }
        List<VendedorImagen> imagenes = new ArrayList<>();
        Vendedor vendedor = vendedorDto.getVendedor();
        for (String imageUrl : imageUrls) {
            VendedorImagen imagen = new VendedorImagen();
            imagen.setImageUrl(imageUrl);
            imagen.setVendedor(vendedor);
            imagenes.add(imagen);
        }
        vendedor.setImagenes(imagenes);
        return new ResponseEntity<>(
                this.service.insert(vendedor),
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
