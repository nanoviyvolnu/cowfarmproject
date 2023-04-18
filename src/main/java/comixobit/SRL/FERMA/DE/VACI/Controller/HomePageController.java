package comixobit.SRL.FERMA.DE.VACI.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/index")
    public String showHomePage(){
        return "index";
    }
}
