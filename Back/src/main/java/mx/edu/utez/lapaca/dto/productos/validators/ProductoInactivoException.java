package mx.edu.utez.lapaca.dto.productos.validators;

public class ProductoInactivoException extends RuntimeException {
    public ProductoInactivoException(String message) {
        super(message);
    }
}
