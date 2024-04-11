package mx.edu.utez.lapaca.dto.productos.validators;

public class InsuficienteStockException extends RuntimeException {
    public InsuficienteStockException(String message) {
        super(message);
    }
}