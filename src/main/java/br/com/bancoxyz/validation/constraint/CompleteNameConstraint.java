package br.com.bancoxyz.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.bancoxyz.validation.validator.CompleteNameValidator;


@Documented
@Constraint(validatedBy = CompleteNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CompleteNameConstraint {

    String message() default "Provide a valid complete name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
