package mx.edu.utez.lapaca.dto.usuarios.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mx.edu.utez.lapaca.dto.usuarios.constraints.IsAdult;

import java.time.LocalDate;
import java.time.Period;

public class IsAdultValidator implements ConstraintValidator<IsAdult, LocalDate> {
    @Override
    public void initialize(IsAdult constraintAnnotation) {
    }
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate now = LocalDate.now();
        Period period = Period.between(value, now);
        return period.getYears() >= 18;
    }
}