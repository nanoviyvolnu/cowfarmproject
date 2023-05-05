package comixobit.SRL.FERMA.DE.VACI.Controller;

import com.lowagie.text.DocumentException;
import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.LivestockProduceRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.LivestockService;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportCowsByPdf;
import comixobit.SRL.FERMA.DE.VACI.Utils.ExportLiveStockByPdf;
import jakarta.servlet.http.HttpServletResponse;
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

    public LivestockProduceController(LivestockProduceRepository livestockProduceRepository, LivestockService livestockService) {
        this.livestockProduceRepository = livestockProduceRepository;
        this.livestockService = livestockService;
    }


    @GetMapping("/listaProduseZootehnice")
    public String allLivestockProduce(Model model){
        List<ProduseZootehniceModel> produseZootehniceModelList = livestockService.selectAllProduceZoo();
        model.addAttribute("produseZootehniceModelList", produseZootehniceModelList);
        return "liveStockProduce/listLivestockProduce";
    }

    @GetMapping("/adaugaProduseZootehnice")
    public String createLivestockProduceForm(ProduseZootehniceModel produseZootehniceModel){
        return "liveStockProduce/createLivestockProduce";
    }

    @PostMapping("/adaugaProduseZootehnice/save")
    public String insertIntoLivestockProduce(ProduseZootehniceModel produseZootehniceModel,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "liveStockProduce/createLivestockProduce";
        }
            livestockService.saveProduceZoo(produseZootehniceModel);
            return "redirect:/adminPanel/listaProduseZootehnice";
    }


    @GetMapping("/stergeProduseZootehnice/{idLot}")
    public String deleteLivestockProduce(@PathVariable("idLot") Integer id) {
        livestockService.deleteById(id);
        return "redirect:/adminPanel/listaProduseZootehnice";
    }

    @GetMapping("/editeazaProduseZootehnice/{idLot}")
    public String createLivestockProduceFormUpdate(@PathVariable("idLot") Integer id, Model model) {
        ProduseZootehniceModel produseZootehniceModel = livestockService.findById(id);
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
