package findYourPlace.controller;

import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    //Creación de usuario
    @RequestMapping(value = "",method = RequestMethod.POST)
    public String createUser(@RequestBody User user) {

        return UserService.createUser(user);
     }

    //Visualizar un usuario
    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public User getUser(@PathVariable int userId) {

        return UserService.getUser(userId);
    }

    //Visualizar lista de listas de lugares del usuario
    @RequestMapping(value = "/{userId}/place_list",method = RequestMethod.GET)
    public List<PlaceList> getUserPlaces(@PathVariable int userId) {

        return UserService.getUserPlaces(userId);
    }

    //Agregar una nueva lista de lugares al usuario
    @RequestMapping(value = "/{userId}/place_list",method = RequestMethod.POST)
    public String setUserPlaces(@PathVariable int userId, @RequestBody PlaceList placeList) {

        return UserService.createUserPlaces(userId, placeList);
    }

    //Eliminar una lista de lugares del usuario
    @RequestMapping(value = "/{userId}/place_list/{placeListId}",method = RequestMethod.DELETE)
    public String deletePlaceList(@PathVariable int userId, @PathVariable int placeListId) {
        return UserService.deleteUserPlaces(userId, placeListId);
    }

    //Cambiar nombre de lista lugares del usuario
    @RequestMapping(value = "/{userId}/place_list/{placeListId}",method = RequestMethod.PATCH)
    public String modifyPlaceList(@PathVariable int userId, @PathVariable int placeListId, @RequestBody String placeListName) {
        return UserService.modifyUserPlaces(userId, placeListId, placeListName);
    }

/*
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
*/
    @RequestMapping(value = "/place_list/{listId}/{placeId}/visited",method = RequestMethod.POST)
    public String visitedPlace(@PathVariable int listId, @PathVariable int placeId) {
        return "Visitado";
    }
}
