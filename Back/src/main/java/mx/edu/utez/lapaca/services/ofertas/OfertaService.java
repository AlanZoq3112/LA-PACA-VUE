package mx.edu.utez.lapaca.services.ofertas;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import mx.edu.utez.lapaca.models.ofertas.Oferta;
import mx.edu.utez.lapaca.models.ofertas.OfertaRepository;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.productos.ProductoRepository;
import mx.edu.utez.lapaca.services.logs.LogService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final ProductoRepository productoRepository;
    private final LogService logService;
    private final Timer timer;
    public OfertaService(OfertaRepository ofertaRepository, ProductoRepository productoRepository, LogService logService) {
        this.ofertaRepository = ofertaRepository;
        this.productoRepository = productoRepository;
        this.logService = logService;
        this.timer = new Timer();
    }

    @PostConstruct
    public void scheduleOfferCheck() {
        OfertaTimerTask ofertaTask = new OfertaTimerTask(ofertaRepository);
        long delay = 0;
        long period = TimeUnit.DAYS.toMillis(1);
        timer.scheduleAtFixedRate(ofertaTask, delay, period);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Oferta> insert(Oferta oferta) {
        if (oferta.getId() != null) {
            Optional<Oferta> exists = ofertaRepository.findById(oferta.getId());
            if (exists.isPresent()) {
                return new CustomResponse<>(null, true, 400, "Error... Oferta ya existente");
            }
        }
        if (oferta.getFechaInicio().after(oferta.getFechaFin())) {
            return new CustomResponse<>(null, true, 400, "Error... Fecha de inicio debe ser anterior a la fecha de fin");
        }
        if (oferta.getPorcentajeDescuento() < 0 || oferta.getPorcentajeDescuento() > 100) {
            return new CustomResponse<>(null, true, 400, "Error... Porcentaje de descuento debe estar entre 0 y 100");
        }
        List<Producto> productosInactivos = new ArrayList<>();
        for (Producto producto : oferta.getProductos()) {
            Optional<Producto> productExists = productoRepository.findById(producto.getId());
            if (!productExists.isPresent()) {
                return new CustomResponse<>(null, true, 400, "Error... Producto con id " + producto.getId() + " no existe");
            }
            Producto product = productExists.get();
            if (product.getEstado() != 3) {
                productosInactivos.add(product);
            }
            List<Oferta> activeOffers = ofertaRepository.findActiveOffersByProductId(producto.getId());
            if (!activeOffers.isEmpty()) {
                return new CustomResponse<>(null, true, 400, "Error... El producto con id " + producto.getId() + " ya tiene una oferta activa");
            }
        }
        if (!productosInactivos.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Error... Los siguientes productos no están activos: ");
            for (Producto productoInactivo : productosInactivos) {
                errorMessage.append(productoInactivo.getId()).append(", ");
            }
            errorMessage.deleteCharAt(errorMessage.length() - 2);
            return new CustomResponse<>(null, true, 400, errorMessage.toString());
        }
        try {
            Oferta savedOferta = ofertaRepository.save(oferta);
            logService.log("Insert", "Oferta registrada", "ofertas");
            return new CustomResponse<>(savedOferta, false, 200, "Oferta registrada");
        } catch (DataAccessException e) {
            return new CustomResponse<>(null, true, 500, "Error interno del servidor al registrar la oferta");
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(null, true, HttpStatus.BAD_REQUEST.value(), "Error... Datos para insertar de oferta ilegal: " + e.getMessage());
        }
    }
    @PreDestroy
    public void closeTimer() {
        timer.cancel();
    }
}
