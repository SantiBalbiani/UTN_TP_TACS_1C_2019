package TACSGrupo1.AppFindUrPlace.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
	
	@RequestMapping(value = "/place", method = RequestMethod.GET, params = {"filtros"})
	public String totalPlacesGet(@RequestParam String filtros)
	{
		return "Cantidad de lugares en el sistema bajo filtros: " + filtros;
	}
	
	

}
