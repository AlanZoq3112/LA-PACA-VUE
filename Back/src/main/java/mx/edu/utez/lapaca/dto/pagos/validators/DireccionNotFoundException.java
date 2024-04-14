package mx.edu.utez.lapaca.dto.pagos.validators;

public class DireccionNotFoundException extends RuntimeException {
    public DireccionNotFoundException(String message) {
        super(message);
    }
}
