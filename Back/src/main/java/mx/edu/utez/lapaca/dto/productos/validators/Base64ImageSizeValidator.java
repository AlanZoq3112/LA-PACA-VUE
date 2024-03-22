package mx.edu.utez.lapaca.dto.productos.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Base64ImageSizeValidator implements ConstraintValidator<ValidBase64ImageSize, String> {

    private static final int MAX_BYTES = 2097152; // 2 MB en bytes

    @Override
    public void initialize(ValidBase64ImageSize constraintAnnotation) {
    }

    @Override
    public boolean isValid(String base64Image, ConstraintValidatorContext context) {
        if (base64Image == null || base64Image.isEmpty()) {
            return true; // Permitir cadenas vacías
        }

        // Decodifica la cadena Base64 y calcula el tamaño en bytes
        byte[] imageData = java.util.Base64.getDecoder().decode(base64Image);
        int imageSizeInBytes = imageData.length;

        // Comprueba si el tamaño de la imagen decodificada es menor o igual al límite máximo
        return imageSizeInBytes <= MAX_BYTES;
    }
}