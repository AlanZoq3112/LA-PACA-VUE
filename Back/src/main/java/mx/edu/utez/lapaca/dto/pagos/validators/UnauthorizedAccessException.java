package mx.edu.utez.lapaca.dto.pagos.validators;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
