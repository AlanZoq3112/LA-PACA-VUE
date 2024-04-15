package mx.edu.utez.lapaca.dto.pagos.exceptions;

public class PagoNoPerteneceAlUsuarioException extends RuntimeException {
    public PagoNoPerteneceAlUsuarioException(String message) {
        super(message);
    }
}