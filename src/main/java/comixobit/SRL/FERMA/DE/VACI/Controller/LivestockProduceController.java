package comixobit.SRL.FERMA.DE.VACI.Controller;

import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.LivestockProduceRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.LivestockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            return "liveStockProduce/createLivestockProduce";
        } else {
            livestockService.saveProduceZoo(produseZootehniceModel);
            return "redirect:/adminPanel/listaProduseZootehnice";
        }
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
        } else {
            livestockService.saveProduceZoo(produseZootehniceModel);
            return "redirect:/adminPanel/listaProduseZootehnice";
        }
    }
}
