package comixobit.SRL.FERMA.DE.VACI.Controller;

import com.lowagie.text.DocumentException;
import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.FeedsRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.FeedsService;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportCowsByPdf;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportFeedsByPdf;
import comixobit.SRL.FERMA.DE.VACI.Utils.FIleUploadUtil;
import jakarta.servlet.http.HttpServletResponse;
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
public class FeedsController {

    private final FeedsRepository feedsRepository;
    private final FeedsService feedsService;

    public FeedsController(FeedsRepository feedsRepository, FeedsService feedsService) {
        this.feedsRepository = feedsRepository;
        this.feedsService = feedsService;
    }

    @GetMapping("/listaFuraje/pagina/{pageNumber}")
    public String getOnePage(Model model,
                             @PathVariable("pageNumber") int currentPage){
        Page<FurajeModel> page = feedsService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<FurajeModel> furajeModelList = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("furajeModelList", furajeModelList);

        return "feeds/listFeeds";
    }

    @GetMapping("/listaFuraje")
    public String getAllPages(Model model){
        return getOnePage(model, 1);
    }

    @GetMapping("/adaugaFuraje")
    public String createFeedsForm(FurajeModel furajeModel){
        return "feeds/createFeed";
    }

    @PostMapping("/adaugaFuraje/save")
    public String insertIntoFeeds(FurajeModel furajeModel,
                                  BindingResult bindingResult,
                                  @RequestParam(value = "image", required = false) MultipartFile multipartFile)
            throws IOException {
        if (bindingResult.hasErrors()) {
            return "feeds/createFeed";
        }else if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            furajeModel.setPhotoCheck(fileName);

            feedsService.saveFeeds(furajeModel);


            String uploadDir = "images/feedsChecks/" + furajeModel.getIdLot();

            FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        return "redirect:/adminPanel/listaFuraje";
    }


    @GetMapping("/stergeFuraje/pagina/{pageNumber}/{idLot}")
    public String deleteFeeds(@PathVariable("idLot") Integer id,
                              @PathVariable("pageNumber") int currentPage,
                              Model model) {
        Page<FurajeModel> page = feedsService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        feedsService.deleteById(id);
        return "redirect:/adminPanel/listaFuraje";
    }

    @GetMapping("/editeazaFuraje/pagina/{pageNumber}/{idLot}")
    public String createFeedsFormUpdate(@PathVariable("idLot") Integer id,
                                        @PathVariable("pageNumber") int currentPage,
                                        Model model) {
        FurajeModel furajeModel = feedsService.findById(id);
        Page<FurajeModel> page = feedsService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("furajeModel", furajeModel);
        return "feeds/updateFeeds";
    }

    @PostMapping("/listaFuraje/editeazaFuraje/save")
    public String updateFeeds(FurajeModel furajeModel,
                              BindingResult bindingResult,
                              @RequestParam(value = "image", required = false) MultipartFile multipartFile1)
            throws IOException {
        if (bindingResult.hasErrors()) {
            return "feeds/updateFeeds";
        }else if (multipartFile1 != null && !multipartFile1.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
            furajeModel.setPhotoCheck(fileName);

            String uploadDir = "images/feedsChecks/" + furajeModel.getIdLot();

            FIleUploadUtil.saveFile(uploadDir, fileName, multipartFile1);
        }
        feedsService.saveFeeds(furajeModel);
        return "redirect:/adminPanel/listaFuraje";
    }

    @GetMapping("/listaFuraje/export-to-pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=furaje_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<FurajeModel> furajeModelList = feedsService.selectAllFeeds();
        ExportFeedsByPdf exporter = new ExportFeedsByPdf(furajeModelList);
        exporter.export(response);
    }
}
