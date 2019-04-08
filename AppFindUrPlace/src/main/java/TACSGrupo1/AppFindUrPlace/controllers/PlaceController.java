package TACSGrupo1.AppFindUrPlace.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/place")
public class PlaceController {
	
	@RequestMapping(value = "", method = RequestMethod.GET, params = { "description", "latitude", "longitude" })
	public String userPost(@RequestParam String description, @RequestParam String latitude, @RequestParam String longitude)
	{
		return "Buscando lugares con filtros: description - " + description + ", latitude - " + longitude + ", longitude - ";
	}
	
	@RequestMapping(value = "/{placeId}/interested", method = RequestMethod.GET)
	public String placeInterestedGet(@PathVariable String placeId)
	{
		return "Cantidad de interesados en el lugar: " + placeId;
	}
	
	

}
