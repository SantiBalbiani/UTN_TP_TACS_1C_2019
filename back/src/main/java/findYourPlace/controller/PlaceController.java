package findYourPlace.controller;

import findYourPlace.service.FourSquareService;
import findYourPlace.entity.Place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/place")
public class PlaceController {

	@Autowired
	private FourSquareService fourSquareService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Place> getPlace(@RequestParam String description, @RequestParam(required = false) Float latitude, @RequestParam(required = false) Float longitude) {
		List<Place> places;

		if(latitude != null && longitude != null) {
			places = fourSquareService.searchPlaces(description, latitude, longitude);
		} else {
			places = fourSquareService.searchPlaces(description);
		}

    	return places;
    }

}
