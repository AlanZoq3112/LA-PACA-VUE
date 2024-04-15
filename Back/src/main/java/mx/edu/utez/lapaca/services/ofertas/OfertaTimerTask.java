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
        List<Oferta> ofertasActivas = ofertaRepository.findAllByEstadoTrue();
        for (Oferta oferta : ofertasActivas) {
            Date fechaActual = new Date();
            if (oferta.getFechaFin().before(fechaActual) || oferta.getFechaFin().equals(fechaActual)) {
                oferta.setEstado(false);
                ofertaRepository.save(oferta);
            }
        }
    }
}