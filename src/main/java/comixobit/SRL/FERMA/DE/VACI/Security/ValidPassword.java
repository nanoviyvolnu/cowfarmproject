package comixobit.SRL.FERMA.DE.VACI.Security;

import comixobit.SRL.FERMA.DE.VACI.Utils.ValidatorPassword;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.passay.PasswordValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidatorPassword.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidPassword {
    String message() default "Alegeti o parola mai puternica!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
