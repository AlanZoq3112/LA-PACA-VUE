package mx.edu.utez.lapaca.controllers.ofertas;

import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.ofertas.OfertaDto;
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
        Oferta oferta = ofertaDto.getOferta();
        CustomResponse<Oferta> customResponse = ofertaService.insert(oferta);
        if (customResponse.isError()) {
            HttpStatus status;
            int statusCode = customResponse.getStatusCode();
            if (statusCode >= 100 && statusCode <= 599) {
                status = HttpStatus.valueOf(statusCode);
            } else {
                status = HttpStatus.BAD_REQUEST;
            }
            return new ResponseEntity<>(customResponse, status);
        } else {
            return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
        }
    }




}
