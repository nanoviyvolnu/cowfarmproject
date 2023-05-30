package comixobit.SRL.FERMA.DE.VACI.Controller;

import com.lowagie.text.DocumentException;
import comixobit.SRL.FERMA.DE.VACI.Models.*;
//import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.ClientsRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.LivestockProduceRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.SellsRepository;
import comixobit.SRL.FERMA.DE.VACI.Service.*;
//import comixobit.SRL.FERMA.DE.VACI.Service.SellsService;
import comixobit.SRL.FERMA.DE.VACI.Utils.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/adminPanel")
public class DashboardController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private FeedsService feedsService;

    @Autowired
    private SellsService sellsService;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private LivestockProduceRepository livestockProduceRepository;

    @Autowired
    private JurnalService jurnalService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/farmMenu/users")
    public String allUsers(Model model){
        List<UserModel> userModelList = usersService.selectAllUsers();
        model.addAttribute("userModelList", userModelList);
        return "dashboard/administration";
    }

    @GetMapping("/farmMenu/users/{idUser}")
    public String deleteCow(@PathVariable("idUser") Integer id) {
        usersService.deleteById(id);
        return "redirect:/adminPanel/farmMenu/users";
    }

    @GetMapping("/farmMenu/cheltuieliFuraje")
    public String allFeeds(Model model){
        List<FurajeModel> furajeModelList = feedsService.selectAllFeeds();
        model.addAttribute("furajeModelList", furajeModelList);
        int furajeSumQuantity = feedsService.selectQuantity();
        model.addAttribute("furajeSumQuantity", furajeSumQuantity);
        int furajeTotalCost = feedsService.selectTotalCostColumns();
        model.addAttribute("furajeTotalCost", furajeTotalCost);
        return "dashboard/feedsExpense";
    }


    @GetMapping("/farmMenu/cheltuieliFuraje/export-to-pdf")
    public void exportToPDFFeeds(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cheltuieli_furaje_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<FurajeModel> furajeModelList = feedsService.selectAllFeeds();
        FeedsTotalExportPdf exporter = new FeedsTotalExportPdf(furajeModelList);
        exporter.export(response);
    }

    @GetMapping("/farmMenu/vanzari/pagina/1")
    public String getAllPagesSells(Model model){
        return getOnePageSales(model, 1);
    }

    @GetMapping("/farmMenu/vanzari/pagina/{pageNumber}")
    public String getOnePageSales(Model model,
                                   @PathVariable("pageNumber") int currentPage) {
        Page<VanzariModel> page = sellsService.selectAllSoldLivestockPage(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<VanzariModel> vanzariModelList = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("vanzariModelList", vanzariModelList).toString();
        return "dashboard/sales";
    }


    @GetMapping("/farmMenu/vanzari/stergeVanzari/{idVanzare}")
    public String deleteSells(@PathVariable("idVanzare") Integer id) {
        sellsService.deleteById(id);
        return "redirect:/adminPanel/farmMenu/vanzari";
    }

    @GetMapping("/farmMenu/vanzari/export-to-pdf")
    public void exportToPDFSells(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vanzari_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<VanzariModel> vanzariModelList = sellsService.selectAllSoldLivestock();
        SallesExportPdf exporter = new SallesExportPdf(vanzariModelList);
        exporter.export(response);
    }

    // clienti
    @GetMapping("/farmMenu/clienti")
    public String allClients(Model model){
        List<ClientiModel> clientiModelList = clientsService.selectAllClient();
        model.addAttribute("clientiModelList", clientiModelList);
        return "dashboard/clients";
    }

    @GetMapping("/farmMenu/clienti/adaugaClienti")
    public String createInsertFormClients(ClientiModel clientiModel) {
        return "dashboard/createClients";
    }

    @PostMapping("/farmMenu/clienti/adaugaClienti/save")
    public String saveClients(ClientiModel clientiModel,
                               BindingResult bindingResult)
            throws IOException {

        if (bindingResult.hasErrors()) {
            return "dashboard/createClients";
        }
            clientsService.saveClient(clientiModel);

            return "redirect:/adminPanel/farmMenu/clienti";
        }

    @GetMapping("/farmMenu/clienti/{idClient}")
    public String deleteClients(@PathVariable("idClient") Integer id) {
        clientsService.deleteById(id);
        return "redirect:/adminPanel/farmMenu/clienti";
    }

    @GetMapping("/farmMenu/clienti/editeazaClienti/{idClient}")
    public String createUpdateClientsForm(@PathVariable("idClient") Integer id, Model model) {
        ClientiModel clientiModel = clientsService.findById(id);
        model.addAttribute("clientiModel", clientiModel);
        return "dashboard/updateClients";
    }


    @PostMapping("/farmMenu/clienti/editeazaClienti/save")
    public String updateClientsSave(ClientiModel clientiModel,
                                     BindingResult bindingResult)
            throws IOException {
        if (bindingResult.hasErrors()) {
            return "dashboard/updateClients";
        }
            clientsService.saveClient(clientiModel);
            return "redirect:/adminPanel/farmMenu/clienti";
        }

    @GetMapping("/farmMenu/clienti/export-to-pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=clienti_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<ClientiModel> clientiModelList = clientsService.selectAllClient();
        ClientsExportPdf exporter = new ClientsExportPdf(clientiModelList);
        exporter.export(response);
    }

    @GetMapping("/farmMenu/notifications/pagina/1")
    public String getAllPagesNotifications(Model model){
        return getOnePageNotification(model, 1);
    }

    @GetMapping("/farmMenu/notifications/pagina/{pageNumber}")
    public String getOnePageNotification(Model model,
                                         @PathVariable("pageNumber") int currentPage) {
        Page<JurnalModel> page = jurnalService.selectAllNotifications(currentPage);
        int totalPage = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<JurnalModel> jurnalModelList = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("jurnalModelList", jurnalModelList).toString();
        return "dashboard/jurnal";
    }



    }
