package FindYourPlace.Controller;

import FindYourPlace.Entity.Place;
import FindYourPlace.Entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AdminController {

    @RequestMapping("/list_comparator/{listId}/{listId2}")
    public List<Place> listComparator(@PathVariable int listId, @PathVariable int listId2) {
        return Arrays.asList(new Place[]{
                new Place("Panqueques de Carlitos")
        });
    }

    @RequestMapping("/place/{placeId}/interested")
    public int interestedUsers(@PathVariable int placeId) {
        return 3;
    }

    @RequestMapping("/dashboard/place")
    public int totalRegisteredPlaces() {
        return 92384;
    }

}
