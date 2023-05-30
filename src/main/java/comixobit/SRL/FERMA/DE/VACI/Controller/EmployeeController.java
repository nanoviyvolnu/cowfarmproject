package comixobit.SRL.FERMA.DE.VACI.Controller;

import com.lowagie.text.DocumentException;
import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.EmployeeRepository;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportCowsByPdf;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportEmployersByPdf;
import comixobit.SRL.FERMA.DE.VACI.Utils.FIleUploadUtil;
import comixobit.SRL.FERMA.DE.VACI.Service.EmployeeService;
import comixobit.SRL.FERMA.DE.VACI.Utils.ValidatorUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String getAllPages(Model model){
        return getOnePage(model, 1);
    }
    @GetMapping("/listaAngajati/pagina/{pageNumber}")
    public String getOnePage(Model model,
                             @PathVariable("pageNumber") int currentPage) {
            Page<LucratorModel> page = employeeService.findPage(currentPage);
            int totalPage = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<LucratorModel> lucratorModelList = page.getContent();

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("totalItems", totalItems);
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

                employeeService.saveEmployee(lucratorModel);

                String uploadDir = "images/employers/" + lucratorModel.getIdLucrator();

                FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }

            return "redirect:/adminPanel/listaAngajati";
        }
    }

    @GetMapping("/stergeAngajat/pagina/{pageNumber}/{idLucrator}")
    public String deleteEmployee(@PathVariable("idLucrator") Integer id,
                                 @PathVariable("pageNumber") int currentPage,
                                 Model model) {
        Page<LucratorModel> page = employeeService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        employeeService.deleteById(id);
        return "redirect:/adminPanel/listaAngajati";
    }

    @GetMapping("/editeazaAngajat/pagina/{pageNumber}/{idLucrator}")
    public String createEmployeeFormUpdate(@PathVariable("idLucrator") Integer id,
                                           @PathVariable("pageNumber") int currentPage,
                                            Model model) {
        LucratorModel lucratorModel = employeeService.findById(id);
        Page<LucratorModel> page = employeeService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
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

                String uploadDir = "images/employers/" + lucratorModel.getIdLucrator();

                FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile1);
            }

            employeeService.updateEmployee(lucratorModel);
            return "redirect:/adminPanel/listaAngajati";
        }
    }

    @GetMapping("/listaAngajati/export-to-pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=angajati_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<LucratorModel> lucratorModelList = employeeService.selectAllEmployee();
        ExportEmployersByPdf exporter = new ExportEmployersByPdf(lucratorModelList);
        exporter.export(response);
    }
}
