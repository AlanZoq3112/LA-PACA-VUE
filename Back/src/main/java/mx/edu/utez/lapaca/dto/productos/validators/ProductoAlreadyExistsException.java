package mx.edu.utez.lapaca.dto.productos.validators;

public class ProductoAlreadyExistsException extends Exception {
    public ProductoAlreadyExistsException(String message) {
        super(message);
    }
}