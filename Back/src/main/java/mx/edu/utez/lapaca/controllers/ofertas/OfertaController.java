package mx.edu.utez.lapaca.controllers.ofertas;

import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.categorias.CategoriaDto;
import mx.edu.utez.lapaca.dto.ofertas.OfertaDto;
import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.ofertas.Oferta;
import mx.edu.utez.lapaca.services.ofertas.OfertaService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-carsi-shop/oferta")
@CrossOrigin(origins = {"*"})
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<Oferta>> insert(@Valid @RequestBody OfertaDto ofertaDto) {
        // Convierte OfertaDto a Oferta usando un método adecuado en OfertaDto
        Oferta oferta = ofertaDto.getOferta();

        // Llama al servicio para insertar la oferta
        CustomResponse<Oferta> customResponse = ofertaService.insert(oferta);

        // Verifica si la operación fue exitosa
        if (customResponse.isError()) {
            // Si ocurrió un error, devuelve una respuesta con el estado HTTP correspondiente
            HttpStatus status;

            // Verifica si el statusCode está dentro del rango de códigos HTTP válidos
            int statusCode = customResponse.getStatusCode();
            if (statusCode >= 100 && statusCode <= 599) {
                // Si es un código válido, conviértelo a HttpStatus
                status = HttpStatus.valueOf(statusCode);
            } else {
                // Si no es un código válido, usa BAD_REQUEST como valor predeterminado
                status = HttpStatus.BAD_REQUEST;
            }

            return new ResponseEntity<>(customResponse, status);
        } else {
            // Si la operación fue exitosa, devuelve la respuesta con estado HTTP 201 (CREATED)
            return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
        }
    }




}
