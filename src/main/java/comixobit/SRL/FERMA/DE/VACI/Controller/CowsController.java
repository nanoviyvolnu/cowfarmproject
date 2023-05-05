package comixobit.SRL.FERMA.DE.VACI.Controller;

import com.lowagie.text.DocumentException;
import comixobit.SRL.FERMA.DE.VACI.Models.UserModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.EmployeeRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportCowsByPdf;
import comixobit.SRL.FERMA.DE.VACI.Utils.FIleUploadUtil;
import comixobit.SRL.FERMA.DE.VACI.Service.EmployeeService;
import comixobit.SRL.FERMA.DE.VACI.Service.CowService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@org.springframework.stereotype.Controller
@RequestMapping("/adminPanel")
public class CowsController {
    private final CowService cowService;
    private final CowRepository cowRepository;

    public CowsController(CowService cowService, CowRepository cowRepository) {
        this.cowService = cowService;
        this.cowRepository = cowRepository;
    }


    @GetMapping("/listaVaci")
    public String allCows(Model model,
                          @RequestParam(value = "rasa", required = false) String rasa) {
        if(rasa == null) {
            List<VacaModel> vacaModelList = cowService.selectAllCows();
            model.addAttribute("vacaModelList", vacaModelList);
        }else{
        List<VacaModel> vacaModelList = cowService.findByRasaCow(rasa);
        model.addAttribute("vacaModelList", vacaModelList);
        }
        return "cows/listCows";
    }

    @GetMapping("/adaugaVaca")
    public String createInsertCowForm(VacaModel vacaModel) {
        return "cows/createCow";
    }


    @PostMapping("/adaugaVaca/save")
    public String saveCow(VacaModel vacaModel,
                                BindingResult bindingResult,
                                @RequestParam(name = "image", required = false) MultipartFile multipartFile)
            throws IOException {
        if(bindingResult.hasErrors()){
            return "cows/createCow";
        } else if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            vacaModel.setPhoto(fileName);

            cowService.saveCow(vacaModel);

            String uploadDir = "images/cows/" + vacaModel.getIdVaca();

            FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        return "redirect:/adminPanel/listaVaci";
    }

    @GetMapping("/stergeVaca/{idVaca}")
    public String deleteCow(@PathVariable("idVaca") Integer id) {
        cowService.deleteById(id);
        return "redirect:/adminPanel/listaVaci";
    }

    @GetMapping("/editeazaVaca/{idVaca}")
    public String createUserFormUpdate(@PathVariable("idVaca") Integer id, Model model) {
        VacaModel vacaModel = cowService.findById(id);
        model.addAttribute("vacaModel", vacaModel);
        return "cows/updateCow";
    }



    @PostMapping("/listaVaci/editeazaVaca/save")
    public String updateCowSave(VacaModel vacaModel,
                                BindingResult bindingResult,
                                @RequestParam(name = "image", required = false) MultipartFile multipartFile1)
            throws IOException {
        if(bindingResult.hasErrors()){
            return "cows/updateCow";
        } else if (multipartFile1 != null && !multipartFile1.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
            vacaModel.setPhoto(fileName);

            String uploadDir = "images/cows/" + vacaModel.getIdVaca();

            FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile1);
        }
        cowService.saveCow(vacaModel);
        return "redirect:/adminPanel/listaVaci";
    }
    @GetMapping("/listaVaci/export-to-pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vaci_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<VacaModel> vacaModelList = cowService.selectAllCows();
        ExportCowsByPdf exporter = new ExportCowsByPdf(vacaModelList);
        exporter.export(response);
    }


}