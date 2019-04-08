package TACSGrupo1.AppFindUrPlace.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list_comparator")
public class ListController {
	
	@RequestMapping(value = "/{listId1}/{listId2}", method = RequestMethod.GET)
	public String listComparisonGet(@PathVariable String listId1, @PathVariable String listId2)
	{
		return "Comparacion de lista : " + listId1 + ", y lista: " + listId2;
	}
	
	

}
