package comixobit.SRL.FERMA.DE.VACI.Controller;

import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.EmployeeRepository;
import comixobit.SRL.FERMA.DE.VACI.Utils.FIleUploadUtil;
import comixobit.SRL.FERMA.DE.VACI.Service.EmployeeService;
import comixobit.SRL.FERMA.DE.VACI.Utils.ValidatorUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/adminPanel")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final ValidatorUtil validatorUtil;


    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService, ValidatorUtil validatorUtil) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.validatorUtil = validatorUtil;
    }

    @InitBinder("form")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validatorUtil);
    }

    @GetMapping("/listaAngajati")
    public String allEmployers(Model model){
        List<LucratorModel> lucratorModelList = employeeService.selectAllEmployee();
        model.addAttribute("lucratorModelList", lucratorModelList);
        return "employee/listEmployee";
    }

    @GetMapping("/adaugaAngajat")
    public String createInsertEmployeeForm(LucratorModel lucratorModel) {
        return "employee/createEmployee";
    }

    @PostMapping("/adaugaAngajat/save")
    public String saveEmployee(LucratorModel lucratorModel,
                               BindingResult bindingResult,
                               @RequestParam(name = "image", required = false) MultipartFile multipartFile)
            throws IOException {

        validatorUtil.validate(lucratorModel, bindingResult);
        if (bindingResult.hasErrors()) {
            return "employee/createEmployee";
        }  else {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                lucratorModel.setPhoto(fileName);

                String uploadDir = "images/employers/" + lucratorModel.getIdLucrator();

                FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
            employeeService.saveEmployee(lucratorModel);

            return "redirect:/adminPanel/listaAngajati";
        }
    }

    @GetMapping("/stergeAngajat/{idLucrator}")
    public String deleteEmployee(@PathVariable("idLucrator") Integer id) {
        employeeService.deleteById(id);
        return "redirect:/adminPanel/listaAngajati";
    }

    @GetMapping("/editeazaAngajat/{idLucrator}")
    public String createEmployeeFormUpdate(@PathVariable("idLucrator") Integer id, Model model) {
        LucratorModel lucratorModel = employeeService.findById(id);
        model.addAttribute("lucratorModel", lucratorModel);
        return "employee/updateEmployee";
    }


    @PostMapping("/listaAngajati/editeazaAngajat/save")
    public String updateEmployeeSave(LucratorModel lucratorModel,
                                     BindingResult bindingResult,
                                     @RequestParam(name = "image", required = false) MultipartFile multipartFile1)
            throws IOException {
        if (bindingResult.hasErrors()) {
            return "employee/updateEmployee";
        }  else {
            if (multipartFile1 != null && !multipartFile1.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
                lucratorModel.setPhoto(fileName);

                employeeService.saveEmployee(lucratorModel);

                String uploadDir = "images/employers/" + lucratorModel.getIdLucrator();

                FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile1);
            }
            return "redirect:/adminPanel/listaAngajati";
        }
    }
}
