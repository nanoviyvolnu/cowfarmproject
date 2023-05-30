package comixobit.SRL.FERMA.DE.VACI.Controller;

import comixobit.SRL.FERMA.DE.VACI.Models.UserModel;
import comixobit.SRL.FERMA.DE.VACI.Service.RegisterService;
import comixobit.SRL.FERMA.DE.VACI.Utils.ValidatorPassword;
import comixobit.SRL.FERMA.DE.VACI.Utils.ValidatorUsers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegisterService registerService;
    private final ValidatorUsers validatorUsers;
    private ValidatorPassword validatorPassword;

    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.addValidators(validatorUsers, (Validator) validatorPassword);
    }

    @Autowired
    public AuthController(RegisterService registerService, ValidatorUsers validatorUsers) {
        this.registerService = registerService;
        this.validatorUsers = validatorUsers;
    }

    @GetMapping("/login")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "auth/login";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("userModel") UserModel userModel) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String performRegistration(@ModelAttribute("userModel") @Valid UserModel userModel,
                                      BindingResult bindingResult) {
        ValidatorPassword validatorPassword = new ValidatorPassword();
        validatorUsers.validate(userModel, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/register";
        }
        registerService.register(userModel);

        return "redirect:/auth/login";
    }



}
