package comixobit.SRL.FERMA.DE.VACI.Controller;

import com.lowagie.text.DocumentException;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaFinalModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
//import comixobit.SRL.FERMA.DE.VACI.Repository.CowFinalRepository;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.*;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportCowsByPdf;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportSoldedDiedCowsByPdf;
import comixobit.SRL.FERMA.DE.VACI.Utils.FIleUploadUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/adminPanel")
public class CowsController {
    @Autowired
    private CowService cowService;
    @Autowired
    private CowRepository cowRepository;
    @Autowired
    private CowFinalService cowFinalService;
    @Autowired
    private SellsService sellsService;

    @GetMapping("/listaVaci/pagina/1")
    public String getAllPages(Model model,
                              @RequestParam(value = "rasa", required = false) String rasa){
        return getOnePage(model, rasa, 1);
    }
    @GetMapping("/listaVaci/pagina/{pageNumber}")
    public String getOnePage(Model model,
                            @RequestParam(value = "rasa", required = false) String rasa,
                            @PathVariable("pageNumber") int currentPage) {
        if(rasa == null) {
            rasa = "";
            Page<VacaModel> page = cowService.findPage(currentPage);
            int totalPage = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<VacaModel> vacaModelList = page.getContent();

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("vacaModelList", vacaModelList);

        }else{
            Page<VacaModel> page = cowService.findByRasaCow(rasa, currentPage);
            int totalPage = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<VacaModel> vacaModelList = page.getContent();
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("vacaModelList", vacaModelList);
            model.addAttribute("rasa", rasa);

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

            vacaModel.calculatePregnancyDate();

            cowService.saveCow(vacaModel);

            String uploadDir = "images/cows/" + vacaModel.getIdVaca();

            FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        return "redirect:/adminPanel/listaVaci/pagina/1";
    }

    @GetMapping("/stergeVaca/pagina/{pageNumber}/{idVaca}")
    public String deleteCow(@PathVariable("idVaca") Integer id,
                            @PathVariable("pageNumber") int currentPage,
                            Model model) {
        Page<VacaModel> page = cowService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        cowService.deleteById(id);
        return "redirect:/adminPanel/listaVaci/pagina/1";
    }

    @GetMapping("/editeazaVaca/pagina/{pageNumber}/{idVaca}")
    public String createUserFormUpdate(@PathVariable("idVaca") Integer id,
                                       @PathVariable("pageNumber") int currentPage,
                                       Model model) {
        VacaModel vacaModel = cowService.findById(id);
        Page<VacaModel> page = cowService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("vacaModel", vacaModel);
        return "cows/updateCow";
    }



    @PostMapping("/editeazaVaca/save")
    public String updateCowSave(VacaModel vacaModel,
                                VacaFinalModel vacaFinalModel,
                                BindingResult bindingResult,
                                @RequestParam(name = "statutul", required = false) String statutul,
                                @RequestParam(name = "image", required = false) MultipartFile multipartFile1)
            throws IOException {


        if(bindingResult.hasErrors()){
            return "cows/updateCow";
        }
        else if(statutul.equals("Mort") || statutul.equals("Taiata")){
            cowFinalService.saveDiedCow(vacaModel);
        }
        else if (multipartFile1 != null && !multipartFile1.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
            vacaModel.setPhoto(fileName);

            String uploadDir = "images/cows/" + vacaModel.getIdVaca();

            FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile1);
        }
        vacaModel.calculatePregnancyDate();
        cowService.saveCow(vacaModel);
        return "redirect:/adminPanel/listaVaci/pagina/1";
    }

    @GetMapping("/vindeVaca/pagina/{pageNumber}/{idVaca}")
    public String showSellForm(@PathVariable("idVaca") int idVaca,
                               @PathVariable("pageNumber") int currentPage,
                                String organizatia,
                                Model model){
        VacaModel vacaModel = cowService.findById(idVaca);
        Page<VacaModel> page = cowService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("vacaModel", vacaModel);
        List<String> organizatiaSelector = sellsService.findByOrganizatia(organizatia);
        model.addAttribute("organizatiaSelector", organizatiaSelector);
        return "cows/sellCow";
    }

    @PostMapping("/vindeVaca/save")
    public String sellCow(VacaFinalModel vacaFinalModel,
                          VacaModel vacaModel,
                          @ModelAttribute VanzariModel vanzariModel,
                          BindingResult bindingResult,
                          @RequestParam(value = "idLot", required = true) int idVaca,
                          @RequestParam(value = "organizatia", required = true) String organizatia,
                          Integer cantitate){
        if(bindingResult.hasErrors()){
            return "cows/sellCow";
        }
        sellsService.saveSoldCows(vanzariModel, idVaca, cantitate, organizatia);
        return "redirect:/adminPanel/listaVaci/pagina/1";
    }


    @GetMapping("/vacileInsarcinate/pagina/{pageNumber}")
    public String getAllPregnatCows(Model model,
                                 @RequestParam(value = "rasa", required = false) String rasa,
                                 @PathVariable("pageNumber") int currentPage) {
        if(rasa == null) {
            rasa = "";
            Page<VacaModel> page = cowService.findPagePregnatCows(currentPage);
            int totalPage = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<VacaModel> vacaModelListPregnatCows = page.getContent();

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("vacaModelListPregnatCows", vacaModelListPregnatCows);

        }else{
            Page<VacaModel> page = cowService.findByRasaPregnatCow(rasa, currentPage);
            int totalPage = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<VacaModel> vacaModelListPregnatCows = page.getContent();
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("vacaModelListPregnatCows", vacaModelListPregnatCows);
            model.addAttribute("rasa", rasa);
        }
        return "cows/pregnatCows";
    }

    @GetMapping("/vacileInsarcinate/pagina/1")
    public String getAllPagesPregnatCows(Model model,
                                         @RequestParam(value = "rasa", required = false) String rasa){
        return getAllPregnatCows(model, rasa, 1);
    }

    @GetMapping("/vacileBolnave/pagina/{pageNumber}")
    public String getAllSickCows(Model model,
                              @RequestParam(value = "rasa", required = false) String rasa,
                              @PathVariable("pageNumber") int currentPage) {
        if(rasa == null) {
            rasa = "";
            Page<VacaModel> page = cowService.findPageSickCows(currentPage);
            int totalPage = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<VacaModel> vacaModelListSickCows = page.getContent();

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("vacaModelListSickCows", vacaModelListSickCows);

        }else{
            Page<VacaModel> page = cowService.findBySickRasaCow(rasa, currentPage);
            int totalPage = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<VacaModel> vacaModelListPregnatCows = page.getContent();
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("vacaModelListPregnatCows", vacaModelListPregnatCows);
            model.addAttribute("rasa", rasa);
        }
        return "cows/sickCows";
    }

    @GetMapping("/vacileBolnave/pagina/1")
    public String getAllPagesSickCows(Model model,
                                      @RequestParam(value = "rasa", required = false) String rasa){
        return getAllSickCows(model, rasa, 1);
    }

    @GetMapping("/vacileMoarte/pagina/{pageNumber}")
    public String getAllDiedCows(Model model,
                                 @RequestParam(value = "rasa", required = false) String rasa,
                                 @PathVariable("pageNumber") int currentPage) {
        if(rasa == null) {
            rasa = "";
            Page<VacaFinalModel> page = cowFinalService.findPageByDiedCows(currentPage);
            int totalPage = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<VacaFinalModel> vacaModelListDiedCows = page.getContent();

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("vacaModelListDiedCows", vacaModelListDiedCows);

        }else{
            Page<VacaFinalModel> page = cowFinalService.findDiedCowByRasa(rasa, currentPage);
            int totalPage = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<VacaFinalModel> vacaModelListDiedCows = page.getContent();
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("vacaModelListDiedCows", vacaModelListDiedCows);
            model.addAttribute("rasa", rasa);
        }
        return "cows/diedCows";
    }

    @GetMapping("/vacileMoarte/pagina/1")
    public String getAllDiedCows(Model model,
                                 @RequestParam(value = "rasa", required = false) String rasa){
        return getAllDiedCows(model, rasa, 1);
    }

    @GetMapping("/listaVaci/export-to-pdf")
    public void exportToPDFActualCows(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vaci_actuale_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<VacaModel> vacaModelList = cowService.activeCows();
        ExportCowsByPdf exporter = new ExportCowsByPdf(vacaModelList);
        exporter.export(response);
    }

    @GetMapping("/vacileInsarcinate/export-to-pdf")
    public void exportToPDFPregnatCows(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vaci_insarcinate_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<VacaModel> vacaModelList = cowService.pregnatCows();
        ExportCowsByPdf exporter = new ExportCowsByPdf(vacaModelList);
        exporter.export(response);
    }


    @GetMapping("/vacileBolnave/export-to-pdf")
    public void exportToPDFSickCows(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vaci_bolnave_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<VacaModel> vacaModelList = cowService.sickCows();
        ExportCowsByPdf exporter = new ExportCowsByPdf(vacaModelList);
        exporter.export(response);
    }

    @GetMapping("/vacileMoarte/export-to-pdf")
    public void exportToPDFDiedCows(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vaci_moarte_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<VacaFinalModel> vacaFinalModel = cowFinalService.diedCows();
        ExportSoldedDiedCowsByPdf exporter = new ExportSoldedDiedCowsByPdf(vacaFinalModel);
        exporter.export(response);
    }

    @GetMapping("/vacileVandute/export-to-pdf")
    public void exportToPDFSoldCows(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vaci_vandute_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<VacaFinalModel> vacaFinalModel = cowFinalService.soldedCows();
        ExportSoldedDiedCowsByPdf exporter = new ExportSoldedDiedCowsByPdf(vacaFinalModel);
        exporter.export(response);
    }
}