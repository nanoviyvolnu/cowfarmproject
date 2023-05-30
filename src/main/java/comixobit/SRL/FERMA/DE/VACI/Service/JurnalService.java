package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.JurnalModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.CowRepository;
import comixobit.SRL.FERMA.DE.VACI.Repository.JurnalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class JurnalService {

    @Autowired
    private CowRepository cowRepository;

    @Autowired
    private JurnalRepository jurnalRepository;
    @Autowired
    private EmailService emailService;


    //    @Scheduled(cron = "0 0 0 * * *") // Start every day at 24 00
    @Scheduled(cron = "0 * * * * *")
    public void checkPregnancyCows() {
        Date currentDate = new Date();

        List<VacaModel> vacaModels = cowRepository.findByDataInsarcinareBefore(currentDate);

        for (VacaModel vaca : vacaModels) {
            String cowCode = vaca.getCodul();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(vaca.getDataInsarcinare());
            calendar.add(Calendar.DAY_OF_YEAR, -7);

            System.out.println(vacaModels);
             try{

                    JurnalModel jurnalModel = new JurnalModel();

                    jurnalModel.setVacaModel(vaca);
                    jurnalModel.setStatus("Insarcinare");

                    jurnalRepository.save(jurnalModel);

                    String email = "tmuncush@gmail.com";
                    String subject = "Salut, " + email + ", vaca cu codul " + cowCode + " ajunge in momentul de insamantare!";
                    String message = "Vaca cu codul de urmarire " + cowCode + " ,peste 7 zile v-a ajunge la momentul de insamantarea, \n" +
                            "interpdindeti toate necesarele pentru a oferi vacii comfortul pentru insamantare!";
                    emailService.sendEmail(email, subject, message);
                }
                catch (Exception exception){
                    System.out.println(exception);
            }
             finally{
                 System.out.println("Message sended!");
             }
        }
    }


    public Page<JurnalModel> selectAllNotifications(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return jurnalRepository.getAllCows(pageable);
    }

}
