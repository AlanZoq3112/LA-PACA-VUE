package mx.edu.utez.lapaca.dto.productos.validators;

public class ProductoInsertException extends RuntimeException {

    public ProductoInsertException() {
        super("Error durante la inserción del producto.");
    }

    public ProductoInsertException(String message) {
        super(message);
    }

    public ProductoInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductoInsertException(Throwable cause) {
        super("Error durante la inserción del producto.", cause);
    }

}