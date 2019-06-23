package findYourPlace.controller;

import findYourPlace.entity.Place;
import findYourPlace.service.FourSquareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/place")
public class PlaceController {

	@Autowired
	private FourSquareService fourSquareService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity getPlace(@RequestParam String description, @RequestParam(required = false) Float latitude, @RequestParam(required = false) Float longitude) {
		List<Place> places;

		if(latitude != null && longitude != null) {
			places = fourSquareService.searchPlaces(description, latitude, longitude);
		} else {
			places = fourSquareService.searchPlaces(description);
		}
		return new ResponseEntity(places, HttpStatus.OK);
    }

	@RequestMapping(value = "/{foursquareId}",method = RequestMethod.GET)
	public ResponseEntity getPlaceFromId(@PathVariable String foursquareId) {
		return new ResponseEntity(fourSquareService.getPlaceById(foursquareId), HttpStatus.OK);
	}

}
