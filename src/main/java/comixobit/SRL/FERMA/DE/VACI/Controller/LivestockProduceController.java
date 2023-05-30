package comixobit.SRL.FERMA.DE.VACI.Controller;

import com.lowagie.text.DocumentException;
import comixobit.SRL.FERMA.DE.VACI.Models.*;
import comixobit.SRL.FERMA.DE.VACI.Repository.LivestockProduceRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.LivestockService;
import comixobit.SRL.FERMA.DE.VACI.Service.SellsService;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportLiveStockByPdf;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/adminPanel")
public class LivestockProduceController {
    private final LivestockProduceRepository livestockProduceRepository;
    private final LivestockService livestockService;

    private final SellsService sellsService;

    public LivestockProduceController(LivestockProduceRepository livestockProduceRepository, LivestockService livestockService, SellsService sellsService) {
        this.livestockProduceRepository = livestockProduceRepository;
        this.livestockService = livestockService;
        this.sellsService = sellsService;
    }


    @GetMapping("/listaProduseZootehnice/pagina/{pageNumber}")
    private String getOnePage(Model model,
                              @PathVariable("pageNumber") int currentPage){
        Page<ProduseZootehniceModel> page = livestockService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<ProduseZootehniceModel> produseZootehniceModelList = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("produseZootehniceModelList", produseZootehniceModelList);

        return "liveStockProduce/listLivestockProduce";
    }

    @GetMapping("/listaProduseZootehnice")
    public String getAllPages(Model model){
        return getOnePage(model, 1);
    }

    @GetMapping("/adaugaProduseZootehnice")
    public String createLivestockProduceForm(ProduseZootehniceModel produseZootehniceModel){
        return "liveStockProduce/createLivestockProduce";
    }

    @PostMapping("/adaugaProduseZootehnice/save")
    public String insertIntoLivestockProduce(ProduseZootehniceModel produseZootehniceModel,
                                             BindingResult bindingResult,
                                             Model model){
        if (bindingResult.hasErrors()) {
            return "liveStockProduce/createLivestockProduce";
        }
        livestockService.saveProduceZoo(produseZootehniceModel);
        return "redirect:/adminPanel/listaProduseZootehnice";
    }


    @GetMapping("/stergeProduseZootehnice/pagina/{pageNumber}/{idLot}")
    public String deleteLivestockProduce(@PathVariable("idLot") Integer id,
                                         @PathVariable("pageNumber") int currentPage,
                                         Model model) {
        Page<ProduseZootehniceModel> page = livestockService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        livestockService.deleteById(id);
        return "redirect:/adminPanel/listaProduseZootehnice";
    }

    @GetMapping("/editeazaProduseZootehnice/pagina/{pageNumber}/{idLot}")
    public String createLivestockProduceFormUpdate(@PathVariable("idLot") Integer id,
                                                   @PathVariable("pageNumber") int currentPage,
                                                   Model model) {
        ProduseZootehniceModel produseZootehniceModel = livestockService.findById(id);
        Page<ProduseZootehniceModel> page = livestockService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("produseZootehniceModel", produseZootehniceModel);
        return "liveStockProduce/updateLivestockProduce";
    }

    @PostMapping("/editeazaProduseZootehnice/save")
    public String updateLivestockProduce(ProduseZootehniceModel produseZootehniceModel,
                                         BindingResult bindingResult,
                                         Model model) {
        if (bindingResult.hasErrors()) {
            return "liveStockProduce/updateLivestockProduce";
        }
        livestockService.saveProduceZoo(produseZootehniceModel);
        return "redirect:/adminPanel/listaProduseZootehnice";
    }

    @GetMapping("/vindeProduseZootehnice/{idLot}")
    public String createSellFromLivestock(@PathVariable("idLot") Integer id,
                                          String organizatia,
                                          Model model) {
        ProduseZootehniceModel produseZootehniceModel = livestockService.findById(id);
        model.addAttribute("produseZootehniceModel", produseZootehniceModel);
        List<String> organizatiaSelector = sellsService.findByOrganizatia(organizatia);
        model.addAttribute("organizatiaSelector", organizatiaSelector);
        return "liveStockProduce/sellLivestock";
    }

    @PostMapping("/vindeProduseZootehnice/save")
    public String saleLivestock(ProduseZootehniceModel produseZootehniceModel,
                                @ModelAttribute VanzariModel vanzariModel,
                                BindingResult bindingResult,
                                @RequestParam(value = "idLot", required = true) int idLot,
                                @RequestParam(value = "organizatia", required = true) String organizatia,
                                @RequestParam(value = "cantitate", required = true) int cantitate){
        if(bindingResult.hasErrors()){
            return "liveStockProduce/sellLivestock";
        }
        sellsService.saveSoldLivestock(vanzariModel, idLot, cantitate, organizatia);
        return "redirect:/adminPanel/listaProduseZootehnice";
    }

    @GetMapping("/listaProduseZootehnice/export-to-pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=produse_zootehnice_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<ProduseZootehniceModel> produseZootehniceModelList = livestockService.selectAllProduceZoo();
        ExportLiveStockByPdf exporter = new ExportLiveStockByPdf(produseZootehniceModelList);
        exporter.export(response);
    }

}
