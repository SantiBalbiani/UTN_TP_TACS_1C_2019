package findYourPlace.controller;

import findYourPlace.entity.User;
import findYourPlace.utils.Encrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody User user) {
            /*  Verificar porque no puedo compilar  
        String pass_to_verify = user.get_password();
        boolean pass_ok = Encrypt.checkPsw(pass_to_verify, **ACA RECUPERAR PASS ENCRIPTADA** )
        if pass_ok{
            return "Contraseña incorrecta";
        }else{
            return "Logeado con éxito";
        } */
        return "Logeado con éxito";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public String logout(@RequestBody User user) {
        return "Deslogeado con éxito";
    }
}
