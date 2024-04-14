package mx.edu.utez.lapaca.dto.productos.validators;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(String message) {
        super(message);
    }

}
