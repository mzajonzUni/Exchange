package pl.zajonz.exchange.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target( { ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyConstraint {
    String message() default "Currency is not available";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
