package TACSGrupo1.AppFindUrPlace.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(value = "", method = RequestMethod.POST, params = { "username", "password" })
	public String userPost(@RequestParam String username, @RequestParam String password)
	{
		return "Creacion de usuario: " + username + ", pass: " + password;
	}
	
	@RequestMapping(value = "/place_list/{listid}/{placeid}", method = RequestMethod.POST)
	public String newListPlacePost(@PathVariable String listid, @PathVariable String placeid)
	{
		return "Se agrego el lugar " + placeid + " a la lista " + listid;
	}
	
	@RequestMapping(value = "/place_list", method = RequestMethod.POST, params = { "name" })
	public String placeListPost(@RequestParam String name)
	{
		return "Se creo la lista de lugares: " + name;
	}
	
	@RequestMapping(value = "/place_list/{listid}", method = RequestMethod.PATCH, params = { "name" })
	public String modifiyListPatch(@PathVariable String listid, @RequestParam String name)
	{
		return "Se modifica la lista " + listid + " con el nombre " + name;
	}
	
	@RequestMapping(value = "/place_list/{listid}", method = RequestMethod.DELETE)
	public String deleteListDelete(@PathVariable String listid)
	{
		return "Se elimina la lista " + listid;
	}
	
	@RequestMapping(value = "/place_list/{listid}/{placeid}/visited", method = RequestMethod.POST)
	public String markListVisitedPlacePost(@PathVariable String listid, @PathVariable String placeid)
	{
		return "Se marca como visitado el lugar " + placeid + " de la lista " + listid;
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String userInfoGet(@PathVariable String userId)
	{
		return "Se obtiene info del usuario: " + userId;
	}
	
	

}
