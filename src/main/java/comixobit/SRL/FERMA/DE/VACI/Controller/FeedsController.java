package comixobit.SRL.FERMA.DE.VACI.Controller;

import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.FeedsRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.FeedsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listaFuraje")
    public String allFeeds(Model model){
        List<FurajeModel> furajeModelList = feedsService.selectAllFeeds();
        model.addAttribute("furajeModelList", furajeModelList);
        return "feeds/listFeeds";
    }

    @GetMapping("/adaugaFuraje")
    public String createFeedsForm(FurajeModel furajeModel){
        return "feeds/createFeed";
    }

    @PostMapping("/adaugaFuraje/save")
    public String insertIntoFeeds(FurajeModel furajeModel,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            return "feeds/createFeed";
        } else {
            feedsService.saveFeeds(furajeModel);
            return "redirect:/adminPanel/listaFuraje";
        }
    }


    @GetMapping("/stergeFuraje/{idLot}")
    public String deleteFeeds(@PathVariable("idLot") Integer id) {
        feedsService.deleteById(id);
        return "redirect:/adminPanel/listaFuraje";
    }

    @GetMapping("/editeazaFuraje/{idLot}")
    public String createFeedsFormUpdate(@PathVariable("idLot") Integer id, Model model) {
        FurajeModel furajeModel = feedsService.findById(id);
        model.addAttribute("furajeModel", furajeModel);
        return "feeds/updateFeeds";
    }

    @PostMapping("/listaFuraje/editeazaFuraje/save")
    public String updateFeeds(FurajeModel furajeModel,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "employee/updateEmployee";
        } else {
            feedsService.saveFeeds(furajeModel);
            return "redirect:/adminPanel/listaFuraje";
        }
    }
}
