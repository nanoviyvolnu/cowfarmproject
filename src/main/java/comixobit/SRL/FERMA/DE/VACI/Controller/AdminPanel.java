package comixobit.SRL.FERMA.DE.VACI.Controller;

import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.EmployeeRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.FeedsRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.SellsRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.CowService;
import comixobit.SRL.FERMA.DE.VACI.Service.EmployeeService;
import comixobit.SRL.FERMA.DE.VACI.Service.FeedsService;
import comixobit.SRL.FERMA.DE.VACI.Service.SellsService;
import jakarta.persistence.Embeddable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class AdminPanel {

    @Autowired
    private CowService cowService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CowRepository cowRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private FeedsService feedsService;
    @Autowired
    private SellsService sellsService;

    @Autowired
    private FeedsRepository feedsRepository;
    @Autowired
    private SellsRepository sellsRepository;

    @GetMapping("/adminPanel")
    public String homePage(Model model,
                           @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                           @RequestParam(name = "month", required = false) Integer month,
                           @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        int totalCows = cowService.selectCountCows();
        model.addAttribute("totalCows", totalCows);
        int totalEmployers = employeeService.selectCountEmployers();
        model.addAttribute("totalEmployers", totalEmployers);
        int furajeTotalCost = feedsService.selectTotalCost();
        model.addAttribute("furajeTotalCost", furajeTotalCost);
        List<Object> vacaModelCountByRasa = cowRepository.getCountByCategorie();
        model.addAttribute("vacaModelCountByRasa", vacaModelCountByRasa);
        List<Object> lucratorModelCountByRemunerare = employeeRepository.getCountByRemunerarePeOra();
        model.addAttribute("lucratorModelCountByRemunerare", lucratorModelCountByRemunerare);

        if (startDate == null || endDate == null) {
            // accounting expennds
            int januaryExpends = feedsService.selectTotalCostJanuary();
            model.addAttribute("januaryExpends", januaryExpends);
            int februaryExpends = feedsService.selectTotalCostFeburary();
            model.addAttribute("februaryExpends", februaryExpends);
            int marchExpends = feedsService.selectTotalCostMarch();
            model.addAttribute("marchExpends", marchExpends);
            int aprilExpends = feedsService.selectTotalCostApril();
            model.addAttribute("aprilExpends", aprilExpends);
            int mayExpends = feedsService.selectTotalCostMay();
            model.addAttribute("mayExpends", mayExpends);
            int juneExpends = feedsService.selectTotalCostJune();
            model.addAttribute("juneExpends", juneExpends);
            int julyExpends = feedsService.selectTotalCostJuly();
            model.addAttribute("julyExpends", julyExpends);
            int augustExpends = feedsService.selectTotalCostAugust();
            model.addAttribute("augustExpends", augustExpends);
            int septemberExpends = feedsService.selectTotalCostSeptember();
            model.addAttribute("septemberExpends", septemberExpends);
            int octoberExpends = feedsService.selectTotalCostOctober();
            model.addAttribute("octoberExpends", octoberExpends);
            int novemberExpends = feedsService.selectTotalCostNovember();
            model.addAttribute("novemberExpends", novemberExpends);
            int decemberExpends = feedsService.selectTotalCostDecember();
            model.addAttribute("decemberExpends", decemberExpends);

            //incomes
            int januaryIncomes = sellsService.selectTotalCostJanuaryIncomes();
            model.addAttribute("januaryIncomes", januaryIncomes);
            int februaryIncomes = sellsService.selectTotalCostFebruaryIncomes();
            model.addAttribute("februaryIncomes", februaryIncomes);
            int marchIncomes = sellsService.selectTotalCostMarchIncomes();
            model.addAttribute("marchIncomes", marchIncomes);
            int aprilIncomes = sellsService.selectTotalCostAprilIncomes();
            model.addAttribute("aprilIncomes", aprilIncomes);
            int mayIncomes = sellsService.selectTotalCostMayIncomes();
            model.addAttribute("mayIncomes", mayIncomes);
            int juneIncomes = sellsService.selectTotalCostJuneIncomes();
            model.addAttribute("juneIncomes", juneIncomes);
            int julyIncomes = sellsService.selectTotalCostJulyIncomes();
            model.addAttribute("julyIncomes", julyIncomes);
            int augustIncomes = sellsService.selectTotalCostAugustIncomes();
            model.addAttribute("augustIncomes", augustIncomes);
            int septemberIncomes = sellsService.selectTotalCostSeptemberIncomes();
            model.addAttribute("septemberIncomes", septemberIncomes);
            int octoberIncomes = sellsService.selectTotalCostOctoberIncomes();
            model.addAttribute("octoberIncomes", octoberIncomes);
            int novemberIncomes = sellsService.selectTotalCostNovemberIncomes();
            model.addAttribute("novemberIncomes", novemberIncomes);
            int decemberIncomes = sellsService.selectTotalCostDecemberIncomes();
            model.addAttribute("decemberIncomes", decemberIncomes);


            //total
            int januaryTotal = sellsService.selectTotalCostJanuaryIncomes() - feedsService.selectTotalCostJanuary();
            model.addAttribute("januaryTotal", januaryTotal);
            int februaryTotal = sellsService.selectTotalCostFebruaryIncomes() - feedsService.selectTotalCostFeburary();
            model.addAttribute("februaryTotal", februaryTotal);
            int marchTotal = sellsService.selectTotalCostMarchIncomes() - feedsService.selectTotalCostMarch();
            model.addAttribute("marchTotal", marchTotal);
            int aprilTotal = sellsService.selectTotalCostAprilIncomes() - feedsService.selectTotalCostApril();
            model.addAttribute("aprilTotal", aprilTotal);
            int mayTotal = sellsService.selectTotalCostMayIncomes() - feedsService.selectTotalCostMay();
            model.addAttribute("mayTotal", mayTotal);
            int juneTotal = sellsService.selectTotalCostJuneIncomes() - feedsService.selectTotalCostJune();
            model.addAttribute("juneTotal", juneTotal);
            int julyTotal = sellsService.selectTotalCostJulyIncomes() - feedsService.selectTotalCostJuly();
            model.addAttribute("julyTotal", julyTotal);
            int augustTotal = sellsService.selectTotalCostAugustIncomes() - feedsService.selectTotalCostAugust();
            model.addAttribute("augustTotal", augustTotal);
            int septemberTotal = sellsService.selectTotalCostSeptemberIncomes() - feedsService.selectTotalCostSeptember();
            model.addAttribute("septemberTotal", septemberTotal);
            int octoberTotal = sellsService.selectTotalCostOctoberIncomes() - feedsService.selectTotalCostOctober();
            model.addAttribute("octoberTotal", octoberTotal);
            int novemberTotal = sellsService.selectTotalCostNovemberIncomes() - feedsService.selectTotalCostNovember();
            model.addAttribute("novemberTotal", novemberTotal);
            int decemberTotal = sellsService.selectTotalCostDecemberIncomes() - feedsService.selectTotalCostDecember();
            model.addAttribute("decemberTotal", decemberTotal);
        }else {

            // accounting expennds
            int januaryExpends = feedsRepository.findByDataProcurariiBetweenJanuary(1, startDate, endDate);
            model.addAttribute("januaryExpends", januaryExpends);
            int februaryExpends = feedsRepository.findByDataProcurariiBetweenFebruary(2, startDate, endDate);
            model.addAttribute("februaryExpends", februaryExpends);
            int marchExpends = feedsRepository.findByDataProcurariiBetweenMarch(3, startDate, endDate);
            model.addAttribute("marchExpends", marchExpends);
            int aprilExpends = feedsRepository.findByDataProcurariiBetweenApril(4, startDate, endDate);
            model.addAttribute("aprilExpends", aprilExpends);
            int mayExpends = feedsRepository.findByDataProcurariiBetweenMay(5, startDate, endDate);
            model.addAttribute("mayExpends", mayExpends);
            int juneExpends = feedsRepository.findByDataProcurariiBetweenJune(6, startDate, endDate);
            model.addAttribute("juneExpends", juneExpends);
            int julyExpends = feedsRepository.findByDataProcurariiBetweenJuly(7, startDate, endDate);
            model.addAttribute("julyExpends", julyExpends);
            int augustExpends = feedsRepository.findByDataProcurariiBetweenAugust(8, startDate, endDate);
            model.addAttribute("augustExpends", augustExpends);
            int septemberExpends = feedsRepository.findByDataProcurariiBetweenSeptember(9, startDate, endDate);
            model.addAttribute("septemberExpends", septemberExpends);
            int octoberExpends = feedsRepository.findByDataProcurariiBetweenOctober(10, startDate, endDate);
            model.addAttribute("octoberExpends", octoberExpends);
            int novemberExpends = feedsRepository.findByDataProcurariiBetweenNovember(11, startDate, endDate);
            model.addAttribute("novemberExpends", novemberExpends);
            int decemberExpends = feedsRepository.findByDataProcurariiBetweenDecember(12, startDate, endDate);
            model.addAttribute("decemberExpends", decemberExpends);

            //incomes
            int januaryIncomes = sellsRepository.findByDataProcurariiBetweenJanuary(1, startDate, endDate);
            model.addAttribute("januaryIncomes", januaryIncomes);
            int februaryIncomes = sellsRepository.findByDataProcurariiBetweenFebruary(2, startDate, endDate);
            model.addAttribute("februaryIncomes", februaryIncomes);
            int marchIncomes = sellsRepository.findByDataProcurariiBetweenMarch(3, startDate, endDate);
            model.addAttribute("marchIncomes", marchIncomes);
            int aprilIncomes = sellsRepository.findByDataProcurariiBetweenApril(4, startDate, endDate);
            model.addAttribute("aprilIncomes", aprilIncomes);
            int mayIncomes = sellsRepository.findByDataProcurariiBetweenMay(5, startDate, endDate);
            model.addAttribute("mayIncomes", mayIncomes);
            int juneIncomes = sellsRepository.findByDataProcurariiBetweenJune(6, startDate, endDate);
            model.addAttribute("juneIncomes", juneIncomes);
            int julyIncomes = sellsRepository.findByDataProcurariiBetweenJuly(7, startDate, endDate);
            model.addAttribute("julyIncomes", julyIncomes);
            int augustIncomes = sellsRepository.findByDataProcurariiBetweenAugust(8, startDate, endDate);
            model.addAttribute("augustIncomes", augustIncomes);
            int septemberIncomes = sellsRepository.findByDataProcurariiBetweenSeptember(9, startDate, endDate);
            model.addAttribute("septemberIncomes", septemberIncomes);
            int octoberIncomes = sellsRepository.findByDataProcurariiBetweenOctober(10, startDate, endDate);
            model.addAttribute("octoberIncomes", octoberIncomes);
            int novemberIncomes = sellsRepository.findByDataProcurariiBetweenNovember(11, startDate, endDate);
            model.addAttribute("novemberIncomes", novemberIncomes);
            int decemberIncomes = sellsRepository.findByDataProcurariiBetweenDecember(12, startDate, endDate);
            model.addAttribute("decemberIncomes", decemberIncomes);


            //total
            int januaryTotal = sellsRepository.findByDataProcurariiBetweenJanuary(1, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(1, startDate, endDate);
            model.addAttribute("januaryTotal", januaryTotal);
            int februaryTotal = sellsRepository.findByDataProcurariiBetweenJanuary(2, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(2, startDate, endDate);
            model.addAttribute("februaryTotal", februaryTotal);
            int marchTotal = sellsRepository.findByDataProcurariiBetweenJanuary(3, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(3, startDate, endDate);
            model.addAttribute("marchTotal", marchTotal);
            int aprilTotal = sellsRepository.findByDataProcurariiBetweenJanuary(4, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(4, startDate, endDate);
            model.addAttribute("aprilTotal", aprilTotal);
            int mayTotal = sellsRepository.findByDataProcurariiBetweenJanuary(5, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(5, startDate, endDate);
            model.addAttribute("mayTotal", mayTotal);
            int juneTotal = sellsRepository.findByDataProcurariiBetweenJanuary(6, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(6, startDate, endDate);
            model.addAttribute("juneTotal", juneTotal);
            int julyTotal = sellsRepository.findByDataProcurariiBetweenJanuary(7, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(7, startDate, endDate);
            model.addAttribute("julyTotal", julyTotal);
            int augustTotal = sellsRepository.findByDataProcurariiBetweenJanuary(8, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(8, startDate, endDate);
            model.addAttribute("augustTotal", augustTotal);
            int septemberTotal = sellsRepository.findByDataProcurariiBetweenJanuary(9, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(9, startDate, endDate);
            model.addAttribute("septemberTotal", septemberTotal);
            int octoberTotal = sellsRepository.findByDataProcurariiBetweenJanuary(10, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(10, startDate, endDate);
            model.addAttribute("octoberTotal", octoberTotal);
            int novemberTotal = sellsRepository.findByDataProcurariiBetweenJanuary(11, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(11, startDate, endDate);
            model.addAttribute("novemberTotal", novemberTotal);
            int decemberTotal = sellsRepository.findByDataProcurariiBetweenJanuary(12, startDate, endDate) - feedsRepository.findByDataProcurariiBetweenJanuary(12, startDate, endDate);
            model.addAttribute("decemberTotal", decemberTotal);
        }
        return "adminPanel";
    }
}
