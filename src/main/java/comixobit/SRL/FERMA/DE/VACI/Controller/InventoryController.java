package comixobit.SRL.FERMA.DE.VACI.Controller;

import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Models.InventarModel;
import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.InventoryRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/adminPanel")
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryService inventoryService, InventoryRepository inventoryRepository) {
        this.inventoryService = inventoryService;
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping("/listaInventar/pagina/{pageNumber}")
    private String getOnePage(Model model,
                              @PathVariable("pageNumber") int currentPage){
        Page<InventarModel> page = inventoryService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<InventarModel> inventarModelList = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("inventarModelList", inventarModelList);

        return "inventoryTools/listTools";
    }

    @GetMapping("/listaInventar")
    public String getAllPages(Model model){
        return getOnePage(model, 1);
    }

    @GetMapping("/listaInventar/adaugaArticole")
    private String addToolsForm(InventarModel inventarModel){
        return "inventoryTools/createTools";
    }

    @PostMapping("/listaInventar/adaugaArticole/save")
    private String addTools(InventarModel inventarModel,
                            BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            return "inventoryTools/createTools";
        }
        inventoryService.saveTools(inventarModel);
        return "redirect:/adminPanel/listaInventar";
    }

    @GetMapping("/listaInventar/editeazaArticole/pagina/{pageNumber}/{idArticol}")
    private String updateToolsForm(@PathVariable("idArticol") int idArticol,
                                   @PathVariable("pageNumber") int pageNumber,
                                   Model model){
        InventarModel inventarModel = inventoryService.findById(idArticol);
        Page<InventarModel> page = inventoryService.findPage(pageNumber);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("inventarModel", inventarModel);
        return "inventoryTools/updateTools";
    }

    @PostMapping("/listaInventar/editeazaArticole/save")
    private String updateTools(InventarModel inventarModel,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "inventoryTools/updateTools";
        }
        inventoryService.saveTools(inventarModel);
        return "redirect:/adminPanel/listaInventar";
    }

    @GetMapping("/listaInventar/stergeArticole/pagina/{pageNumber}/{idArticol}")
    private String deleteTools(@PathVariable("idArticol") Integer idArticol,
                               @PathVariable("pageNumber") int currentPage,
                               Model model){
        Page<InventarModel> page = inventoryService.findPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalItems", totalItems);
        inventoryService.deleteById(idArticol);
        return "redirect:/adminPanel/listaInventar";
    }
}
