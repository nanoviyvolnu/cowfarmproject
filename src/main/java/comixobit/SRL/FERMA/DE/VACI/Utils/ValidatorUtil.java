package comixobit.SRL.FERMA.DE.VACI.Utils;

import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import comixobit.SRL.FERMA.DE.VACI.Service.EmployeeService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ValidatorUtil implements Validator {

    private final EmployeeService employeeService;

    public ValidatorUtil(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return LucratorModel.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LucratorModel lucratorModel = (LucratorModel) target;

        if (employeeService.findByIdnp(lucratorModel.getIdnp())) {
            errors.rejectValue("idnp", "idnp.duplicate", "Acest IDNP există deja!");
        }
    }

}
