package comixobit.SRL.FERMA.DE.VACI.Utils;

import comixobit.SRL.FERMA.DE.VACI.Security.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class ValidatorPassword implements ConstraintValidator<ValidPassword, String> {


    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                    // length between 8 and 16 characters
                new LengthRule(8, 16),
                    // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                    // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                    // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                    // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),
                    // no whitespace
                new WhitespaceRule(),
                    // rejects passwords that contain a sequence of >= 5 characters alphabetical  (e.g. abcdef)
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                    // rejects passwords that contain a sequence of >= 5 characters numerical   (e.g. 12345)
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)
            ));
        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        constraintValidatorContext.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
