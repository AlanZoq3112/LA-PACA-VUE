package mx.edu.utez.lapaca.dto.usuarios.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import mx.edu.utez.lapaca.dto.usuarios.validators.IsAdultValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsAdultValidator.class)
public @interface IsAdult {
    String message() default "Error... debe ser mayor de edad";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}