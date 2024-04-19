package mx.edu.utez.lapaca.dto.comentarios.exceptions;

public class CarritoNoPerteneAlUsuarioException extends RuntimeException {
    public CarritoNoPerteneAlUsuarioException(String message) {
        super(message);
    }
}