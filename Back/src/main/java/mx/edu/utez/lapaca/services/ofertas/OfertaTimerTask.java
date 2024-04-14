package mx.edu.utez.lapaca.services.ofertas;


import mx.edu.utez.lapaca.models.ofertas.Oferta;
import mx.edu.utez.lapaca.models.ofertas.OfertaRepository;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class OfertaTimerTask extends TimerTask {
    private final OfertaRepository ofertaRepository;

    public OfertaTimerTask(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    @Override
    public void run() {
        // Obtén todas las ofertas activas
        List<Oferta> ofertasActivas = ofertaRepository.findAllByEstadoTrue();

        // Recorre todas las ofertas activas
        for (Oferta oferta : ofertasActivas) {
            Date fechaActual = new Date();

            // Verifica si la oferta ha alcanzado su fecha de finalización
            if (oferta.getFechaFin().before(fechaActual) || oferta.getFechaFin().equals(fechaActual)) {
                // Si la oferta ha alcanzado su fecha de finalización, desactívala
                oferta.setEstado(false);
                ofertaRepository.save(oferta);
            }
        }
    }
}