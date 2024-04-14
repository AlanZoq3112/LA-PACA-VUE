package mx.edu.utez.lapaca.controllers.pagos;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.pagos.PagoDto;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.carritos.CarritoRepository;
import mx.edu.utez.lapaca.models.direcciones.DireccionRepository;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.security.dto.email.EmailDto;
import mx.edu.utez.lapaca.security.services.email.EmailService;
import mx.edu.utez.lapaca.services.pagos.PagoService;
import mx.edu.utez.lapaca.services.usuarios.UsuarioService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import mx.edu.utez.lapaca.utils.StripePaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-carsi-shop/pago")
@CrossOrigin(origins = {"*"})
public class PagoController {
    private final PagoService service;
    private final CarritoRepository carritoRepository;
    private final DireccionRepository direccionRepository;
    private final UsuarioService usuarioService;
    private final EmailService emailService;



    public PagoController(PagoService service, CarritoRepository carritoRepository, DireccionRepository direccionRepository, UsuarioService usuarioService, EmailService emailService) {
        this.service = service;
        this.carritoRepository = carritoRepository;

        this.direccionRepository = direccionRepository;
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }
    @PostMapping("/insertarFormaPago")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COMPRADOR', 'ROLE_VENDEDOR')")
    public ResponseEntity<CustomResponse<Pago>> insert(@Valid @RequestBody PagoDto pagoDto) {
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
            carrito.setIdpago(idPago);
            // guardar el pago en la pinche bd
            carritoRepository.save(carrito);
            EmailDto emailDto = new EmailDto();
            emailDto.setEmail(carrito.getUsuario().getEmail());
            emailDto.setFullName(carrito.getUsuario().getNombre());
            emailDto.setSubject("Confirmación de compra en CarsiShop");
            emailDto.setBody("Se ha realizado el pago exitosamente para el pedido con ID " + carrito.getId() +
                    ".<br>ID de pago: " + idPago +
                    "<br>Monto total: $" + carrito.getMonto() +
                    "<br>Estado del pedido: " + carrito.getEstado());
            // envíar el fokin correo electrónico
            emailService.sendMail(emailDto);

            return "Pago exitoso. ID de pago: " + idPago;
        } catch (StripePaymentException e) {
            return "Error al procesar el pago: " + e.getMessage();
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