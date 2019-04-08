package FindYourPlace.Controller;

import java.util.concurrent.atomic.AtomicLong;

import FindYourPlace.Entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "Estas en el home del sitio";
    }
}
