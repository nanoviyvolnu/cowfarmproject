package comixobit.SRL.FERMA.DE.VACI.Utils;

import comixobit.SRL.FERMA.DE.VACI.Models.UserModel;
import comixobit.SRL.FERMA.DE.VACI.Service.UsersService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ValidatorUsers implements Validator {

    private final UsersService usersService;

    public ValidatorUsers(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserModel userModel = (UserModel) target;

        try{
            usersService.loadUserByUsername(userModel.getEmail());
        }catch (UsernameNotFoundException ignored){
            return;
        }

        errors.rejectValue("email", "", "Acest email este deja utilizat!");
    }
}
