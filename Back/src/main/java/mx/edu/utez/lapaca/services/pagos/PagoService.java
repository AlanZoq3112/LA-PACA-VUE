package mx.edu.utez.lapaca.services.pagos;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import mx.edu.utez.lapaca.models.cantidad_pagos.CantidadPago;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.pagos.PagoRepository;
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


    public PagoService(PagoRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

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
                    "Error... argumento ilegal" + e.getMessage()
            );
        }
    }




    public String procesarPago(CantidadPago cantidadPago) throws StripePaymentException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int) (cantidadPago.getMonto() * 100)); // la cantidad se expresa en centavos
        params.put("currency", "usd");
        params.put("description", "Pago por producto: " + cantidadPago.getProducto().getNombre());
        params.put("source", "tok_visa"); // token generado por Stripe.js o Stripe Elements

        try {
            Charge charge = Charge.create(params);
            return charge.getId();
        } catch (StripeException e) {
            throw new StripePaymentException("Error al procesar el pago: " + e.getMessage());
        }
    }





}
