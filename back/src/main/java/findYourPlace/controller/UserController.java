package findYourPlace.controller;

import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;

import java.util.List;
import java.util.NoSuchElementException;

import findYourPlace.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import findYourPlace.entity.Place;
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //Creación de usuario
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {
        return new ResponseEntity(userService.createUser(user), HttpStatus.OK);
     }

    //Visualizar un usuario
    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable String userId) {
        return new ResponseEntity(userService.getUser(userId), HttpStatus.OK);
    }

    //Borrar usuario
    @RequestMapping(value = "/{userId}",method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }


    //Visualizar lista de listas de lugares del usuario
    @RequestMapping(value = "/{userId}/place_list",method = RequestMethod.GET)
    public List<PlaceList> getUserPlaces(@PathVariable String userId) {
        return userService.getUserPlaces(userId);
    }

    //Agregar una nueva lista de lugares al usuario
    @RequestMapping(value = "/{userId}/place_list",method = RequestMethod.POST)
    public ResponseEntity createUserPlaces(@PathVariable String userId, @RequestBody PlaceList placeList) {
        return new ResponseEntity(userService.createUserPlaces(userId, placeList), HttpStatus.OK);
    }

    //Eliminar una lista de lugares del usuario
    @RequestMapping(value = "/{userId}/place_list/{placeListName}",method = RequestMethod.DELETE)
    public ResponseEntity deletePlaceList(@PathVariable String userId, @PathVariable String placeListName) {
        return new ResponseEntity(userService.deleteUserPlaces(userId, placeListName), HttpStatus.OK);
    }

    //Cambiar nombre de lista lugares del usuario
    @RequestMapping(value = "/{userId}/place_list/{placeListCurrentName}",method = RequestMethod.PATCH)
    public User modifyPlaceList(@PathVariable String userId, @PathVariable String placeListCurrentName, @RequestBody String placeListName) {
        return userService.modifyUserPlaces(userId, placeListCurrentName, placeListName);
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
@RequestMapping(value = "/{userId}/place_list/{placeListId}",method = RequestMethod.POST)
public ResponseEntity markPlaceAsVisited
(@PathVariable String userId, @PathVariable String placeListId, @RequestBody Place place)
    {
        return new ResponseEntity(userService.markPlaceAsVisited(userId, placeListId, place), HttpStatus.OK);
    }
}
