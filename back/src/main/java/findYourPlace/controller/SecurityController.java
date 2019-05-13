package findYourPlace.controller;

import findYourPlace.entity.LoginRequest;
import findYourPlace.entity.PruebaTokenRequest;
import findYourPlace.entity.User;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.service.TokenService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class SecurityController {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TokenService tokenService;

    @CrossOrigin
    @PostMapping(value = "/login")
    //@RequestMapping(value = "/login")
    public String login(@RequestBody LoginRequest login) {
        //Llamar a loginForm
    	
    	User usuarioObtenido = userDao.findByUsername(login.getUsername());
    	
    	if (usuarioObtenido == null)
    	{
    		return "Usuario buscado " + login.getUsername() + " no encontrado";
    	}
    	else
    	{
    		return "Usuario buscado " + login.getUsername() + " encontrado y el token es: " + tokenService.getToken(usuarioObtenido);
    	}
    	
        
    }
    
    @PostMapping(value = "/prueba")
    public String prueba(@RequestBody PruebaTokenRequest pruebaTokenRequest) {
        return "El token pertenece al usuario: " + tokenService.getUsername(pruebaTokenRequest.getToken());
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public String logout(@RequestBody User user) {
        return "Deslogeado con éxito";
    }
}
