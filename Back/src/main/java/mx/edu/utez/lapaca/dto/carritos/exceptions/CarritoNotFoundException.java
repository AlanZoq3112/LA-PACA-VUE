package mx.edu.utez.lapaca.dto.carritos.exceptions;

public class CarritoNotFoundException extends RuntimeException {
    public CarritoNotFoundException(String message) {
        super(message);
    }
}