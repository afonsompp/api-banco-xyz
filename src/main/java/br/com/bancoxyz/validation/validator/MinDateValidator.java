package br.com.bancoxyz.validation.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.bancoxyz.validation.constraint.MinDateConstraint;

public class MinDateValidator implements ConstraintValidator<MinDateConstraint, LocalDate> {

    private Integer minAge;

    @Override
    public void initialize(MinDateConstraint constraint) {
        minAge = constraint.minAge() >= 0 ? constraint.minAge() : 0;
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext cxt) {
        LocalDate minDate = LocalDate.now().withYear(LocalDate.now().getYear() - minAge);
        return date == null || (date.isEqual(minDate) || date.isBefore(minDate));
    }
}
