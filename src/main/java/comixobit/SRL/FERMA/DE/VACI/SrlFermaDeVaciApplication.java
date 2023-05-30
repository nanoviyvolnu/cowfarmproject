package comixobit.SRL.FERMA.DE.VACI;

import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import comixobit.SRL.FERMA.DE.VACI.Service.CowService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SrlFermaDeVaciApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrlFermaDeVaciApplication.class, args);
	}
}
