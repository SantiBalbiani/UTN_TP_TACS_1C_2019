package findYourPlace.controller;

import findYourPlace.entity.LoginRequest;
import findYourPlace.entity.PruebaTokenRequest;
import findYourPlace.entity.User;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.service.TokenService;
import findYourPlace.utils.Encrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class SecurityController {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TokenService tokenService;

    @CrossOrigin
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequest login) throws ResponseStatusException, NoSuchAlgorithmException {

    	
    	User usuarioObtenido = userDao.findByUsername(login.getUsername());
    	
    	if (usuarioObtenido == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario o contraseña no válidos.");
    	}
    	
    	if(!Encrypt.checkPsw(login.getPassword(), usuarioObtenido.getPassword())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario o contraseña no válidos.");
    	}

    	return new ResponseEntity(tokenService.getToken(usuarioObtenido), HttpStatus.OK);
    	
    }
    
    
    @PostMapping(value = "/prueba")
    public String prueba(@RequestBody PruebaTokenRequest pruebaTokenRequest) {
        return "El token pertenece al usuario: " + tokenService.getUsername(pruebaTokenRequest.getToken());
    }
    

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResponseEntity logout(@RequestBody User user) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
