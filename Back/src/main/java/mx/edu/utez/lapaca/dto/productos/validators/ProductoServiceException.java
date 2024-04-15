package mx.edu.utez.lapaca.dto.productos.validators;

public class ProductoServiceException extends RuntimeException {
    public ProductoServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}