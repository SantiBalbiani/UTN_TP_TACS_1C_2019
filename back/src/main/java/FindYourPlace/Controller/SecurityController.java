package FindYourPlace.Controller;

import FindYourPlace.Entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SecurityController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        return "Logeado con éxito";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public String logout(@RequestBody User user) {
        return "Deslogeado con éxito";
    }
}
