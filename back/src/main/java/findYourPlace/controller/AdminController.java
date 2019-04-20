package findYourPlace.controller;

import findYourPlace.entity.Place;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
public class AdminController {

    @RequestMapping("/list_comparator/{listId}/{listId2}")
    public List<Place> listComparator(@PathVariable int listId, @PathVariable int listId2) {
        return Arrays.asList(new Place[]{
                new Place("Panqueques de Carlitos")
        });
    }

    @RequestMapping("/place/{placeId}/interested")
    public HashMap<String, Integer> interestedUsers(@PathVariable int placeId) {
        HashMap<String, Integer> response = new HashMap<>();

        response.put("interestedUsers", 3);

        return response;
    }

    @RequestMapping("/dashboard/place")
    public HashMap<String, Integer> totalRegisteredPlaces() {
        HashMap<String, Integer> response = new HashMap<>();

        response.put("totalRegisteredPlaces", 12312);

        return response;
    }

}
