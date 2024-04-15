package mx.edu.utez.lapaca.dto.direcciones.exceptions;

public class DireccionNoPerteneceAlUsuarioException extends RuntimeException {
    public DireccionNoPerteneceAlUsuarioException(String message) {
        super(message);
    }
}