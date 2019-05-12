package findYourPlace.controller;

import findYourPlace.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class SecurityController {

    @CrossOrigin
    @RequestMapping(value = "/login/{userLogIn}")
    public String login(@PathVariable String userLogIn) {
        //Llamar a loginForm
        return "El usuario " + userLogIn + " se ha logueado con éxito";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public String logout(@RequestBody User user) {
        return "Deslogeado con éxito";
    }
}
