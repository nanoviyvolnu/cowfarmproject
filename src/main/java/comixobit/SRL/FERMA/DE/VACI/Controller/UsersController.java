package comixobit.SRL.FERMA.DE.VACI.Controller;

import comixobit.SRL.FERMA.DE.VACI.Models.GroupsModel;
import comixobit.SRL.FERMA.DE.VACI.Models.GrupuriVaciModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.GroupsCowsRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.GroupsRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.CowService;
import comixobit.SRL.FERMA.DE.VACI.Service.GroupsCowService;
import comixobit.SRL.FERMA.DE.VACI.Service.GroupsServices;
import comixobit.SRL.FERMA.DE.VACI.Specification.CowSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UsersController {
    @Autowired
    private CowService cowService;

    @Autowired
    private GroupsServices groupsServices;

    @Autowired
    private GroupsCowsRepository groupsCowsRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private CowRepository cowRepository;
    @Autowired
    private GroupsCowService groupsCowService;

    @GetMapping("/index/users")
    public String indexPageUsers(){
        return "registeredUsers/registeredUsers";
    }

    @GetMapping("/listaVaci")
    public String allCows(Model model) {
        List<VacaModel> vacaModelList = cowService.selectAllCows();
        model.addAttribute("vacaModelList", vacaModelList);
        return "registeredUsers/listCowsUsers";
    }

    @GetMapping("/listaGrupuri")
    public String groups(Model model, Integer idGroup, Integer idGrup){
        List<GroupsModel> groupsModelList = groupsServices.selectAllGroups();
        model.addAttribute("groupsModelList", groupsModelList);
        int count = groupsCowService.totalCountCowsGroup(idGroup);
        model.addAttribute("count", count);
        return "registeredUsers/cowsGroups";
    }


    @GetMapping("/adaugaGrup")
    public String addGroups(Model model,
                            @RequestParam(required = false) String rasa,
                            String genul, String statutul, String formaDeAchizitie,
                            String categorie){
        List<String> rasaSelectorList = cowService.findByRasa(rasa);
        model.addAttribute("rasaSelectorList", rasaSelectorList);
        List<String> categorieSelectorList = cowService.findByCategorie(categorie);
        model.addAttribute("categorieSelectorList", categorieSelectorList);
        List<String> formaDeAchizitieSelectorList = cowService.findByFormaDeAchizite(formaDeAchizitie);
        model.addAttribute("formaDeAchizitieSelectorList", formaDeAchizitieSelectorList);
        List<String> statutSelectorList = cowService.findByStatut(statutul);
        model.addAttribute("statutSelectorList", statutSelectorList);
        model.addAttribute("groupsModel", new GroupsModel());
        return "registeredUsers/createGroups";
    }


    @PostMapping("/adaugaGrup/save")
    public String saveGroups(@ModelAttribute GroupsModel groupsModel,
                             BindingResult bindingResult,
                             @RequestParam(required = false) String rasa,
                             @RequestParam(required = false) String genul,
                             @RequestParam(required = false) String statutul,
                             @RequestParam(required = false) String formaAchizitie,
                             @RequestParam(required = false) String categorie,
                             @RequestParam(required = false) String operator,
                             @RequestParam(required = false) String operator1,
                             @RequestParam(required = false) Integer masaKgInitiala,
                             @RequestParam(required = false) Integer masaKgMoment){

        if(bindingResult.hasErrors()){
            return "/registeredUsers/createGroups";
        }
        else{
            groupsServices.saveGroup(groupsModel);

            List<VacaModel> filteredCows = cowService.findCowsByFilter(rasa, genul, statutul, formaAchizitie, categorie, operator, operator1 ,masaKgInitiala, masaKgMoment);
            for(VacaModel vacaModel : filteredCows){
                GrupuriVaciModel grupuriVaciModel = new GrupuriVaciModel();
                grupuriVaciModel.setVacaModel(vacaModel);
                grupuriVaciModel.setGroupsModel(groupsModel);
                groupsCowsRepository.save(grupuriVaciModel);
            }

            return "redirect:/listaGrupuri";
        }
    }

    @GetMapping("/listaGrupuri/{idGrup}")
    public String getFormById(@PathVariable int idGrup,
                              Model model){
            GroupsModel groupsModel = groupsServices.findById(idGrup);
            List<GrupuriVaciModel> memberships = groupsCowsRepository.findByGroupsModel(groupsModel);
            List<VacaModel> vacaModelList = memberships.stream().map(GrupuriVaciModel::getVacaModel).collect(Collectors.toList());

            model.addAttribute("groupsModel", groupsModel);
            model.addAttribute("vacaModelList", vacaModelList);
        return "registeredUsers/showCowsCriterias";
    }

    @Transactional
    @GetMapping("/stergeGrup/{idGrup}")
    public String deleteCow(@PathVariable int idGrup) {
        GroupsModel groupsModel = groupsServices.findById(idGrup);
        groupsModel.getGrupuriVaciModels().forEach(grupuriVaciModel -> {
            grupuriVaciModel.setGroupsModel(null);
        });
        groupsModel.getGrupuriVaciModels().clear();
        groupsRepository.delete(groupsModel);
        return "redirect:/listaGrupuri";

    }
}
