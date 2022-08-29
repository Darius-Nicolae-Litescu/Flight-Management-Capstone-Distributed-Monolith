package org.darius.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MessageValidator.class)
public @interface WordsNotAllowedConstraint {

    String message() default "The message cannot contain vulgar words";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}