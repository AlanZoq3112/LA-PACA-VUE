package mx.edu.utez.lapaca.services.bitacora;

import mx.edu.utez.lapaca.models.bitacora.Bitacora;
import mx.edu.utez.lapaca.models.bitacora.BitacoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BitacoraService {
    private final BitacoraRepository bitacoraRepository;

    public BitacoraService(BitacoraRepository bitacoraRepository){
        this.bitacoraRepository = bitacoraRepository;
    }

    public  void registrarLog(Bitacora bitacora){
        bitacoraRepository.save(bitacora);
    }
    public List<Bitacora> getAll(){
        return bitacoraRepository.findAll();
    }
}
