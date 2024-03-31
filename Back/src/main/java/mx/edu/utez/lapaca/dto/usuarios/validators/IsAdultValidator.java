package mx.edu.utez.lapaca.dto.usuarios.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mx.edu.utez.lapaca.dto.usuarios.constraints.IsAdult;

import java.time.LocalDate;
import java.time.Period;

public class IsAdultValidator implements ConstraintValidator<IsAdult, LocalDate> {

    // Sobreescribe initialize
    @Override
    public void initialize(IsAdult constraintAnnotation) {
        // No se requiere inicialización especial en este validador
    }

    // Sobreescribe isValid
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Se considera válido si es nulo
        }

        // Realiza la validación y retorna true o false
        LocalDate now = LocalDate.now();
        Period period = Period.between(value, now);
        return period.getYears() >= 18;
    }
}