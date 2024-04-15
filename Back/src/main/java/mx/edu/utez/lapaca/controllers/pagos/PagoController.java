package mx.edu.utez.lapaca.controllers.pagos;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.pagos.PagoDto;
import mx.edu.utez.lapaca.dto.pagos.validators.UnauthorizedAccessException;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.carritos.CarritoRepository;
import mx.edu.utez.lapaca.models.direcciones.DireccionRepository;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.ProductoRepository;
import mx.edu.utez.lapaca.security.dto.email.EmailDto;
import mx.edu.utez.lapaca.security.services.email.EmailService;
import mx.edu.utez.lapaca.services.logs.LogService;
import mx.edu.utez.lapaca.services.pagos.PagoService;
import mx.edu.utez.lapaca.services.usuarios.UsuarioService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import mx.edu.utez.lapaca.utils.StripePaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-carsi-shop/pago")
@CrossOrigin(origins = {"*"})
public class PagoController {
    private final PagoService service;
    private final CarritoRepository carritoRepository;
    private final DireccionRepository direccionRepository;
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final LogService logService;
    private final ProductoRepository productoRepository;

    public PagoController(PagoService service, CarritoRepository carritoRepository, DireccionRepository direccionRepository, UsuarioService usuarioService, EmailService emailService, LogService logService, ProductoRepository productoRepository) {
        this.service = service;
        this.carritoRepository = carritoRepository;
        this.direccionRepository = direccionRepository;
        this.usuarioService = usuarioService;
        this.emailService = emailService;
        this.logService = logService;
        this.productoRepository = productoRepository;
    }

    @PostMapping("/insertarFormaPago")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Pago>> insert(@Valid @RequestBody PagoDto pagoDto) {
        return new ResponseEntity<>(
                this.service.insert(pagoDto.getPago()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/getAllMetodosPago")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<List<Pago>>> getAllMetodosPago() {
        return new ResponseEntity<>(
                this.service.getAllMetodosPago(),
                HttpStatus.OK
        );
    }
    @GetMapping("/mis-metodos-pago")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_COMPRADOR')")
    public ResponseEntity<CustomResponse<List<Pago>>> getAllMeotodosPagosByCurrentUser() {
        return new ResponseEntity<>(
                service.getAllMetodoPagoByCurrentUser(),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/deleteMetodoPago")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Pago>> delete(@Valid @RequestBody Map<String, Long> requestBody){
        Long id = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.deleteById(id),
                HttpStatus.OK
        );
    }


    @PostMapping("/realizar-pago")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_COMPRADOR')")
    public String realizarPago(@Valid @RequestBody Carrito carrito) {
        try {
            String idPago = service.procesarPago(carrito);
            carrito.setIdpago(idPago);
            carritoRepository.save(carrito);
            logService.log("Get", "Se ha efectuado una compra con " +
                    "el id de pago: " + idPago,"carritos");
            EmailDto emailDto = new EmailDto();
            emailDto.setEmail(carrito.getUsuario().getEmail());
            emailDto.setFullName(carrito.getUsuario().getNombre());
            emailDto.setSubject("Confirmación de compra en CarsiShop");
            emailDto.setBody("Se ha realizado el pago exitosamente" +
                    "<br>ID de pago: " + idPago +
                    "<br>Monto total: $" + carrito.getMonto() +
                    "<br>Estado del pedido: " + carrito.getEstado());
            emailService.sendMail(emailDto);

            return "Pago exitoso. ID de pago: " + idPago;
        } catch (StripePaymentException e) {
            return "Error al procesar el pago: " + e.getMessage();
        }
    }

    @PostMapping("/marcar-como-entregado")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_COMPRADOR')")
    public ResponseEntity<String> marcarComoEntregado(@Valid @RequestBody Carrito carrito, Authentication authentication) {
        String username = authentication.getName();
        try {
            service.marcarComoEntregado(carrito.getId(), username);
            EmailDto emailDto = new EmailDto();
            emailDto.setEmail(carrito.getUsuario().getEmail());
            emailDto.setFullName(carrito.getUsuario().getNombre());
            emailDto.setSubject("Confirmación de entrega en CarsiShop");
            emailDto.setBody("Su pedido ha sido entregado satisfactoriamente." +
                    "<br>ID del pedido: " + carrito.getId() +
                    "<br>Estado del pedido: Entregado");

            emailService.sendMail(emailDto);

            return ResponseEntity.ok("El pedido ha sido marcado como entregado correctamente.");
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("No tienes permiso para modificar este carrito.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El pedido no está en camino y no puede ser marcado como entregado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al marcar el pedido como entregado: " + e.getMessage());
        }
    }

    @GetMapping("/getAllPedidos")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<List<Carrito>>> getAll() {
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/mis-pedidos")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_VENDEDOR', 'ROLE_COMPRADOR')")
    public ResponseEntity<CustomResponse<List<Carrito>>> getAllProductosByCurrentUser() {
        return new ResponseEntity<>(
                service.getAllByCurrentUser(),
                HttpStatus.OK
        );
    }

}