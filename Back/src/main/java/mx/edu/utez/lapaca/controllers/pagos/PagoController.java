package mx.edu.utez.lapaca.controllers.pagos;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.pagos.PagoDto;
import mx.edu.utez.lapaca.dto.pagos.validators.DireccionNotFoundException;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.carritos.CarritoRepository;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.direcciones.DireccionRepository;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.services.pagos.PagoService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import mx.edu.utez.lapaca.utils.StripePaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-carsi-shop/pago")
@CrossOrigin(origins = {"*"})
public class PagoController {
    private final PagoService service;
    private final CarritoRepository carritoRepository;
    private final DireccionRepository direccionRepository;

    public PagoController(PagoService service, CarritoRepository carritoRepository, DireccionRepository direccionRepository) {
        this.service = service;
        this.carritoRepository = carritoRepository;

        this.direccionRepository = direccionRepository;
    }
    @PostMapping("/insertarFormaPago")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Pago>> insert(@Valid @RequestBody PagoDto pagoDto){
        return new ResponseEntity<>(
                this.service.insert(pagoDto.getPago()),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/realizar-pago")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_COMPRADOR')")
    public String realizarPago(@Valid @RequestBody Carrito carrito) {
        try {
            // procesar el pago con Stripe
            String idPago = service.procesarPago(carrito);

            // guardar el pago en la pinche bd
            carritoRepository.save(carrito);

            return "Pago exitoso. ID de pago: " + idPago;
        } catch (StripePaymentException e) {
            return "Error al procesar el pago: " + e.getMessage();
        }
    }

}