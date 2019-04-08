package FindYourPlace.Controller;

import FindYourPlace.Entity.PlaceList;
import FindYourPlace.Entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "",method = RequestMethod.POST)
    public String createUser(@RequestBody User user) {
        return "Usuario creado con éxito";
    }

    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public String getUser(@PathVariable int userId) {
        return "Usuario creado con éxito";
    }

    @RequestMapping(value = "/place_list",method = RequestMethod.POST)
    public PlaceList createPlaceList(@RequestBody String name) {
        return new PlaceList(name, new User("Usuario actual", "fakePassword"));
    }

    @RequestMapping(value = "/place_list",method = RequestMethod.PATCH)
    public PlaceList updatePlaceList(@RequestBody String name) {
        return new PlaceList(name, new User("Usuario actual", "fakePassword"));
    }

    @RequestMapping(value = "/place_list",method = RequestMethod.DELETE)
    public String deletePlaceList(@RequestBody String name) {
        return "PlaceList eliminada";
    }

    @RequestMapping(value = "/place_list/{listId}/{placeId}",method = RequestMethod.POST)
    public PlaceList addPlaceToPlaceList(@PathVariable int listId, @PathVariable int placeId) {
        return new PlaceList("Test", new User("Usuario actual", "fakePassword"));
    }

    @RequestMapping(value = "/place_list/{listId}/{placeId}/visited",method = RequestMethod.POST)
    public String visitedPlace(@PathVariable int listId, @PathVariable int placeId) {
        return "Visitado";
    }
}
