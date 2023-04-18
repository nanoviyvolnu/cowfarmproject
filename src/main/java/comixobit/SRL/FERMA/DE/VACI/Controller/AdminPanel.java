package comixobit.SRL.FERMA.DE.VACI.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanel {
    @GetMapping("/adminPanel")
    public String homePage() {
        return "adminPanel";
    }
}
