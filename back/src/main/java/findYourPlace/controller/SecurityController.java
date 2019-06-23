package findYourPlace.controller;

import findYourPlace.entity.LoginRequest;
import findYourPlace.entity.PruebaTokenRequest;
import findYourPlace.entity.TokenResponse;
import findYourPlace.entity.User;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.service.TokenService;
import findYourPlace.utils.Encrypt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.node.ObjectNode;


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
    	
    	//return new
    	//return new ResponseEntity(tokenService.getToken(usuarioObtenido), HttpStatus.OK);
    	return new ResponseEntity(new TokenResponse(tokenService.getToken(usuarioObtenido)), HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/prueba",method = RequestMethod.GET)
    public ResponseEntity prueba(@RequestHeader(value = "token") String request) {
    	
        //return "El token pertenece al usuario: " + tokenService.getUsername(request);
        return new ResponseEntity("El token pertenece al usuario: " + tokenService.getUsername(request) + " con rol:" + tokenService.getRol(request), HttpStatus.OK);
    }
    

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResponseEntity logout(@RequestBody User user) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
