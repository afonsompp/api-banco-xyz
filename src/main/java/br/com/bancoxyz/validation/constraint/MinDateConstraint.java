package br.com.bancoxyz.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.bancoxyz.validation.validator.MinDateValidator;

@Documented
@Constraint(validatedBy = MinDateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinDateConstraint {

    String message() default "Provide date less than minimum";

    Class<?>[] groups() default {};

    int minAge() default 0;

    Class<? extends Payload>[] payload() default {};
}
