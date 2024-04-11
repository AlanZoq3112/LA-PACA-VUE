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
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.pagos.PagoRepository;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.productos.ProductoRepository;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.usuarios.UsuarioRepository;
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

    private final UsuarioRepository usuarioRepository;

    private final ProductoRepository productoRepository;


    public PagoService(PagoRepository repository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }


    //insertar forma de pago
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




    //stripe
    @Transactional(rollbackFor = {StripePaymentException.class})
    public String procesarPago(Carrito carrito) throws StripePaymentException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Obtener el nombre de usuario

        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
        carrito.setUsuario(usuario.get());

        // Obtener el producto asociado al carrito por su ID
        Optional<Producto> productoOptional = productoRepository.findById(carrito.getProducto().getId());

        if (!productoOptional.isPresent()) {
            throw new ProductoNotFoundException("No se encontró el producto con el ID especificado.");
        }

        Producto producto = productoOptional.get();

        // Verificar si el producto está activo
        if (producto.getEstado() != 3) { // Si el estado no es activo
            throw new ProductoInactivoException("El producto no se encuentra activo y no puede ser comprado.");
        }

        // Verificar si hay suficiente stock disponible
        if (carrito.getCantidad() > producto.getStock()) {
            throw new InsuficienteStockException("No hay suficiente stock disponible para este producto.");
        }

        // Calcular el monto basado en la cantidad y el precio del producto
        double monto = carrito.getCantidad() * producto.getPrecio();
        carrito.setMonto(monto);

        // Reducir el stock del producto
        producto.setStock(producto.getStock() - carrito.getCantidad());
        productoRepository.save(producto);


        // Crear parámetros para la creación del cargo en Stripe
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (monto * 100)); // La cantidad se expresa en centavos
        chargeParams.put("currency", "mxn");
        chargeParams.put("description", "Pago por producto: " + producto.getNombre());
        chargeParams.put("source", "tok_visa"); // Token generado por Stripe.js o Stripe Elements

        try {
            // Crear el cargo en Stripe
            ChargeCreateParams params = ChargeCreateParams.builder()
                    .setAmount((long) (monto * 100))
                    .setCurrency("mxn")
                    .setDescription("Pago por producto: " + producto.getNombre())
                    .setSource("tok_visa")
                    .build();
            Charge charge = Charge.create(params);
            return charge.getId();
        } catch (StripeException e) {
            throw new StripePaymentException("Error al procesar el pago: " + e.getMessage());
        }
    }
}
