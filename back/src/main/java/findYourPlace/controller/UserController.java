package findYourPlace.controller;

import com.mongodb.util.JSON;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;

import java.util.List;

import findYourPlace.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //Creación de usuario
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity(user,HttpStatus.OK);
        } catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errorDescription",e.getMessage());
            return new ResponseEntity(jsonObject.toString(),HttpStatus.CONFLICT);
        }
     }

    //Visualizar un usuario
    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable String userId) {
        try {
            return new ResponseEntity(userService.getUser(userId), HttpStatus.OK);
        } catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("description","El usuario no existe");
            return new ResponseEntity(jsonObject.toString(),HttpStatus.CONFLICT);
        }
    }

    //Visualizar lista de listas de lugares del usuario
    @RequestMapping(value = "/{userId}/place_list",method = RequestMethod.GET)
    public List<PlaceList> getUserPlaces(@PathVariable String userId) {

        return userService.getUserPlaces(userId);
    }

    //Agregar una nueva lista de lugares al usuario
    @RequestMapping(value = "/{userId}/place_list",method = RequestMethod.POST)
    public String setUserPlaces(@PathVariable String userId, @RequestBody PlaceList placeList) {

        return userService.createUserPlaces(userId, placeList);
    }

    //Eliminar una lista de lugares del usuario
    @RequestMapping(value = "/{userId}/place_list/{placeListId}",method = RequestMethod.DELETE)
    public String deletePlaceList(@PathVariable String userId, @PathVariable int placeListId) {
        return userService.deleteUserPlaces(userId, placeListId);
    }

    //Cambiar nombre de lista lugares del usuario
    @RequestMapping(value = "/{userId}/place_list/{placeListId}",method = RequestMethod.PATCH)
    public String modifyPlaceList(@PathVariable String userId, @PathVariable int placeListId, @RequestBody String placeListName) {
        return userService.modifyUserPlaces(userId, placeListId, placeListName);
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
