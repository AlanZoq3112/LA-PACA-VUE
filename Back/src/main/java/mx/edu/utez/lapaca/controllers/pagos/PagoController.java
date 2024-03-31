package mx.edu.utez.lapaca.controllers.pagos;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.pagos.PagoDto;
import mx.edu.utez.lapaca.models.cantidad_pagos.CantidadPago;
import mx.edu.utez.lapaca.models.cantidad_pagos.CantidadPagoRepository;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.services.pagos.PagoService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import mx.edu.utez.lapaca.utils.StripePaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-carsi-shop/pago")
@CrossOrigin(origins = {"*"})
public class PagoController {
    private final PagoService service;
    private final CantidadPagoRepository cantidadPagoRepository;

    public PagoController( PagoService service, CantidadPagoRepository cantidadPagoRepository) {
        this.service = service;
        this.cantidadPagoRepository = cantidadPagoRepository;
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
    public String realizarPago(@Valid @RequestBody CantidadPago cantidadPago) {
        try {
            // procesar el pago con Stripe
            String idPago = service.procesarPago(cantidadPago);

            // guardar el pago en la pinche bd
            cantidadPagoRepository.save(cantidadPago);

            return "Pago exitoso. ID de pago: " + idPago;
        } catch (StripePaymentException e) {
            return "Error al procesar el pago: " + e.getMessage();
        }
    }
}