package br.com.bancoxyz.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.bancoxyz.validation.constraint.CompleteNameConstraint;

public class CompleteNameValidator implements ConstraintValidator<CompleteNameConstraint, String> {

    @Override
    public void initialize(CompleteNameConstraint constraint) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext cxt) {
        return name.equals(null) ||
        name.matches("^([a-zA-Z]{3,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{3,})?"
            + "\\s?([a-zA-Z]{3,})?\\s?([a-zA-Z]{3,})?)");
    }
}
