package TACSGrupo1.AppFindUrPlace.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, params = { "username", "password" })
	public String loginPost(@RequestParam String username, @RequestParam String password)
	{
		return "Logeo de usuario: " + username + ", pass: " + password;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST, params = { "username", "password" })
	public String logoutPost(@RequestParam String username, @RequestParam String password)
	{
		return "Se elimina sesion de usuario: " + username + ", pass: " + password;
	}

}
