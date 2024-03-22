package mx.edu.utez.lapaca.dto.productos.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Base64ImageSizeValidator.class)
@Documented
public @interface ValidBase64ImageSize {

    String message() default "El tamaño de la imagen no puede exceder los 2 MB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}