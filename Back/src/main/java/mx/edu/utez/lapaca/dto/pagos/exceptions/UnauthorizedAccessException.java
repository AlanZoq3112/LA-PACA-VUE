package mx.edu.utez.lapaca.dto.pagos.exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
