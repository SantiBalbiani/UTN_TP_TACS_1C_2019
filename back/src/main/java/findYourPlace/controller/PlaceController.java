package findYourPlace.controller;

import findYourPlace.entity.Place;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/place")
public class PlaceController {

    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Place> getPlace(@RequestParam String description, @RequestParam(required = false) Float latitude, @RequestParam(required = false) Float longitude) {

        return Arrays.asList(new Place[]{
                new Place("Panqueques de Carlitos"),
                new Place("Obelisco")
        });
    }
}
