package findYourPlace.controller;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.UserService;
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

    //Agregar una nueva lista de lugares al usuario
    @RequestMapping(value = "/{userId}/place_list",method = RequestMethod.POST)
    public ResponseEntity createPlaceList(@PathVariable String userId, @RequestBody PlaceList placeList) {
        return new ResponseEntity(userService.createUserPlaces(userId, placeList), HttpStatus.OK);
    }

    //Visualizar lista de listas de lugares del usuario
    @RequestMapping(value = "/{userId}/place_list",method = RequestMethod.GET)
    public ResponseEntity getPlaceList(@PathVariable String userId) {
        return new ResponseEntity(userService.getUserPlaces(userId),HttpStatus.OK);
    }

    //Eliminar una lista de lugares del usuario
    @RequestMapping(value = "/{userId}/place_list/{placeListName}",method = RequestMethod.DELETE)
    public ResponseEntity deletePlaceList(@PathVariable String userId, @PathVariable String placeListName) {
        return new ResponseEntity(userService.deleteUserPlaces(userId, placeListName), HttpStatus.OK);
    }

    //Obtener una lista de lugares del usuario
    @RequestMapping(value = "/{userId}/place_list/{placeListName}",method = RequestMethod.GET)
    public ResponseEntity getPlaceListByName(@PathVariable String userId, @PathVariable String placeListName) {
        return new ResponseEntity(userService.getUserPlacesByName(userId, placeListName), HttpStatus.OK);
    }

    //Cambiar nombre de lista lugares del usuario
    @RequestMapping(value = "/{userId}/place_list/{placeListCurrentName}/{placeListName}",method = RequestMethod.PATCH)
    public ResponseEntity modifyPlaceList(@PathVariable String userId, @PathVariable String placeListCurrentName, @PathVariable String placeListName) {
        return new ResponseEntity(userService.modifyUserPlaces(userId, placeListCurrentName, placeListName),HttpStatus.OK);
    }

    //Agregar lugar a lista
    @RequestMapping(value = "/{userId}/place_list/{placeListName}",method = RequestMethod.PUT)
    public ResponseEntity addPlaceToPlaceList(@PathVariable String userId, @PathVariable String placeListName, @RequestBody Place place) {
        return new ResponseEntity(userService.addPlaceToPlaceList(userId, placeListName, place),HttpStatus.OK);
    }

    //Obtener lugar de lista
    @RequestMapping(value = "/{userId}/place_list/{placeListName}/place/{placeId}",method = RequestMethod.GET)
    public ResponseEntity getPlaceFromPlaceList(@PathVariable String userId, @PathVariable String placeListName, @PathVariable String placeId) {
        return new ResponseEntity(userService.getPlaceFromPlaceList(userId, placeListName, placeId),HttpStatus.OK);
    }

    //Eliminar lugar de lista
    @RequestMapping(value = "/{userId}/place_list/{placeListName}/place/{placeId}",method = RequestMethod.DELETE)
    public ResponseEntity deletePlaceFromPlaceList(@PathVariable String userId, @PathVariable String placeListName, @PathVariable String placeId) {
        return new ResponseEntity(userService.deletePlaceFromPlaceList(userId, placeListName, placeId),HttpStatus.OK);
    }

    //Marcar lugar como visitado
    @RequestMapping(value = "/{userId}/place_list/{placeListName}/place/{placeId}",method = RequestMethod.PATCH)
    public ResponseEntity markPlaceAsVisited(@PathVariable String userId, @PathVariable String placeListName, @PathVariable String placeId){
       return new ResponseEntity(userService.markPlaceAsVisited(userId, placeListName, placeId), HttpStatus.OK);
    }
}
