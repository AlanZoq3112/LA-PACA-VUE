package mx.edu.utez.lapaca.services.pagos;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import jakarta.annotation.PostConstruct;
import mx.edu.utez.lapaca.dto.productos.validators.InsuficienteStockException;
import mx.edu.utez.lapaca.dto.productos.validators.ProductoInactivoException;
import mx.edu.utez.lapaca.dto.productos.validators.ProductoNotFoundException;
import mx.edu.utez.lapaca.models.carritos.Carrito;
import mx.edu.utez.lapaca.models.carritos.CarritoRepository;
import mx.edu.utez.lapaca.models.carritos.EstadoPedido;
import mx.edu.utez.lapaca.models.direcciones.Direccion;
import mx.edu.utez.lapaca.models.direcciones.DireccionRepository;
import mx.edu.utez.lapaca.models.itemCarrito.ItemCarrito;
import mx.edu.utez.lapaca.models.ofertas.Oferta;
import mx.edu.utez.lapaca.models.ofertas.OfertaRepository;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.pagos.PagoRepository;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.productos.ProductoRepository;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.usuarios.UsuarioRepository;
import mx.edu.utez.lapaca.services.logs.LogService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import mx.edu.utez.lapaca.utils.StripePaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PagoService {


    @Value("${stripe.secretKey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
    private final PagoRepository repository;
    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final DireccionRepository direccionRepository;
    private final PagoRepository pagoRepository;
    private final OfertaRepository ofertaRepository;
    private final LogService logService;


    public PagoService(PagoRepository repository, CarritoRepository carritoRepository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository, DireccionRepository direccionRepository, PagoRepository pagoRepository, OfertaRepository ofertaRepository, LogService logService) {
        this.repository = repository;
        this.carritoRepository = carritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.direccionRepository = direccionRepository;
        this.pagoRepository = pagoRepository;
        this.ofertaRepository = ofertaRepository;
        this.logService = logService;
    }


    //METODO PAGO
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Pago> insert(Pago pago) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Obtener el nombre de usuario

            Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

            pago.setUsuario(usuario.get());


            // verificar si el producto ya existe
            Optional<Pago> exists = repository.findByNumero(pago.getNumero());
            if (exists.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "Error... Tarjeta ya registrada"
                );
            }
            Pago savedPago = repository.save(pago);
            logService.log("Post", "Se ha registrado un método de pago","pagos");

            return new CustomResponse<>(
                    savedPago,
                    false,
                    200,
                    "Tarjeta registrada exitosamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al registrar la tarjeta"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para insertar una forma de pago ilegal" + e.getMessage()
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Pago>> getAllMetodosPago() {
        return new CustomResponse<>(
                this.pagoRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Pago>> getAllMetodoPagoByCurrentUser() {
        // Obtener el nombre de usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Buscar al usuario por su correo electrónico
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Obtener los productos creados por el usuario
            List<Pago> pagos = pagoRepository.findByUsuario(usuario);
            logService.log("Get", "Usuario ha solicitado ver sus métodos de pago","pagos");
            return new CustomResponse<>(
                    pagos,
                    false,
                    200,
                    "OK"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Usuario no encontrado"
            );
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Pago> deleteById(Long id) {
        try {
            Optional<Pago> pagoId = pagoRepository.findById(id);
            if (!pagoId.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "El pago con el id " + id + " no existe"
                );
            }
            Pago pago = pagoId.get();

            // Desvincula el método de pago de todos los carritos que lo referencian
            carritoRepository.updatePagoToNullByPagoId(id);

            pagoRepository.delete(pago);
            logService.log("Delete", "Método de pago con el id: " + pagoId + " sido " +
                    "eliminado","pagos");

            return new CustomResponse<>(
                    null,
                    false,
                    200,
                    "El método de pago con el id " + id + " ha sido eliminado correctamente"
            );
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al eliminar el método de pago"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para eliminar un método de pago incorrectos" + e.getMessage()
            );
        }
    }


    //stripe
    @Transactional(rollbackFor = {StripePaymentException.class})
    public String procesarPago(Carrito carrito) throws StripePaymentException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Obtener el nombre de usuario

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
        if (!usuarioOptional.isPresent()) {
            throw new RuntimeException ("No se encontró el usuario con el correo electrónico especificado.");
        }
        Usuario usuario = usuarioOptional.get();
        carrito.setUsuario(usuario);

        double montoTotal = 0;
        for (ItemCarrito item : carrito.getItems()) {
            Optional<Producto> productoOptional = productoRepository.findById(item.getProducto().getId());
            if (!productoOptional.isPresent()) {
                throw new ProductoNotFoundException("No se encontró el producto con el ID especificado.");
            }
            Producto producto = productoOptional.get();
            if (producto.getEstado() != 3) {
                throw new ProductoInactivoException("El producto '" + producto.getNombre() + "' no se encuentra activo y no puede ser comprado.");
            }
            if (item.getCantidad() > producto.getStock()) {
                throw new InsuficienteStockException("No hay suficiente stock disponible para el producto '" + producto.getNombre() + "'.");
            }
            double subtotal = item.getCantidad() * producto.getPrecio();

            // Aplicar descuento de ofertas activas si existen
            List<Oferta> ofertasActivas = ofertaRepository.findActiveOffersByProductId(producto.getId());
            if (!ofertasActivas.isEmpty()) {
                double descuentoTotal = 0;
                for (Oferta oferta : ofertasActivas) {
                    descuentoTotal += (producto.getPrecio() * item.getCantidad() * (oferta.getPorcentajeDescuento() / 100));
                }
                subtotal -= descuentoTotal;
            }

            montoTotal += subtotal;
            producto.setStock(producto.getStock() - item.getCantidad());
            carrito.setMonto(montoTotal);
            item.setCarrito(carrito);
        }
        //direccion me lleva la verga no me sale nd ah no ya xd
        Optional<Direccion> direccionOptional = direccionRepository.findById(carrito.getDireccion().getId());
        System.out.println(direccionOptional);
        if (!direccionOptional.isPresent() || !direccionOptional.get().getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("La dirección seleccionada no pertenece al usuario autenticado.");
        }

        Optional<Pago> pagoOptional = pagoRepository.findById(carrito.getPago().getId());
        if (!pagoOptional.isPresent() || !pagoOptional.get().getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("El pago seleccionado no pertenece al usuario autenticado.");
        }
        // Antes de guardar el carrito, establece el estado como "PENDIENTE"
        carrito.setEstado(EstadoPedido.EN_CAMINO);
        carritoRepository.save(carrito);

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (montoTotal * 100)); // La cantidad se expresa en centavos
        chargeParams.put("currency", "mxn");
        chargeParams.put("description", "Pago por productos en el carrito");
        chargeParams.put("source", "tok_visa"); // Token generado por Stripe.js o Stripe Elements

        try {
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount((long) (montoTotal * 100))
                    .setCurrency("mxn")
                    .setDescription("Pago por productos en el carrito")
                    .setSource("tok_visa")
                    .build();
            Charge charge = Charge.create(params);
            return charge.getId();
        } catch (StripeException e) {
            throw new StripePaymentException("Error al procesar el pago: " + e.getMessage());
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Carrito>> getAll() {
        return new CustomResponse<>(
                this.carritoRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }


    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Carrito>> getAllByCurrentUser() {
        // Obtener el nombre de usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Buscar al usuario por su correo electrónico
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Obtener los productos creados por el usuario
            String userEmail = usuario.getEmail(); // Obtener el correo electrónico del usuario

            List<Carrito> carritos = carritoRepository.findByUsuario(usuario);
            logService.log("Get", "El usuario con el correo "
                    + userEmail + "ha solicitado ver su historial de pagos","carritos");
            return new CustomResponse<>(
                    carritos,
                    false,
                    200,
                    "OK"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "Usuario no encontrado"
            );
        }
    }


}
