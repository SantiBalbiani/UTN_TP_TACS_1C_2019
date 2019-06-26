package findYourPlace.controller;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.TokenService;
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

    @Autowired
    TokenService tokenService;

    //Creación de usuario
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {
        return new ResponseEntity(userService.createUser(user), HttpStatus.OK);
     }

    //Visualizar un usuario
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseEntity getUser(@RequestHeader(value = "Authorization") String request) {
        String username = tokenService.getUsername(request);
        return new ResponseEntity(userService.getUserByUsername(username), HttpStatus.OK);
    }

    //Borrar usuario
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestHeader(value = "Authorization") String request) {
        String username = tokenService.getUsername(request);
        userService.deleteUser(username);
        return new ResponseEntity(HttpStatus.OK);
    }

    //Agregar una nueva lista de lugares al usuario
    @RequestMapping(value = "/place_list",method = RequestMethod.POST)
    public ResponseEntity createPlaceList(@RequestHeader(value = "Authorization") String request, @RequestBody String listName) {
        String username = tokenService.getUsername(request);
        return new ResponseEntity(userService.createUserPlaces(username, listName), HttpStatus.OK);
    }

    //Visualizar lista de listas de lugares del usuario
    @RequestMapping(value = "/place_list",method = RequestMethod.GET)
    public ResponseEntity getPlaceList(@RequestHeader(value = "Authorization") String request) {
        String username = tokenService.getUsername(request);
        return new ResponseEntity(userService.getUserPlaces(username),HttpStatus.OK);
    }

    //Eliminar una lista de lugares del usuario
    @RequestMapping(value = "/place_list/{placeListName}",method = RequestMethod.DELETE)
    public ResponseEntity deletePlaceList(@RequestHeader(value = "Authorization") String request, @PathVariable String placeListName) {
        String username = tokenService.getUsername(request);
        return new ResponseEntity(userService.deleteUserPlaces(username, placeListName), HttpStatus.OK);
    }

    //Obtener una lista de lugares del usuario
    @RequestMapping(value = "/place_list/{placeListName}",method = RequestMethod.GET)
    public ResponseEntity getPlaceListByName(@RequestHeader(value = "Authorization") String request, @PathVariable String placeListName) {
        String username = tokenService.getUsername(request);
        return new ResponseEntity<PlaceList>(userService.getUserPlacesByName(username, placeListName), HttpStatus.OK);
    }

    //Cambiar nombre de lista lugares del usuario
    @RequestMapping(value = "/place_list/{placeListCurrentName}/{placeListName}",method = RequestMethod.PATCH)
    public ResponseEntity modifyPlaceList(@RequestHeader(value = "Authorization") String request, @PathVariable String placeListCurrentName, @PathVariable String placeListName) {
        String username = tokenService.getUsername(request);
        return new ResponseEntity(userService.modifyUserPlaces(username, placeListCurrentName, placeListName),HttpStatus.OK);
    }

    //Agregar lugar a lista
    @RequestMapping(value = "/place_list/{placeListName}",method = RequestMethod.PUT)
    public ResponseEntity addPlaceToPlaceList(@RequestHeader(value = "Authorization") String request, @PathVariable String placeListName, @RequestBody Place place) {
        String username = tokenService.getUsername(request);
        return new ResponseEntity(userService.addPlaceToPlaceList(username, placeListName, place),HttpStatus.OK);
    }

    //Obtener lugar de lista
    @RequestMapping(value = "/place_list/{placeListName}/place/{placeId}",method = RequestMethod.GET)
    public ResponseEntity getPlaceFromPlaceList(@RequestHeader(value = "Authorization") String request, @PathVariable String placeListName, @PathVariable String placeId) {
        String username = tokenService.getUsername(request);
        return new ResponseEntity(userService.getPlaceFromPlaceList(username, placeListName, placeId),HttpStatus.OK);
    }

    //Eliminar lugar de lista
    @RequestMapping(value = "/place_list/{placeListName}/place/{placeId}",method = RequestMethod.DELETE)
    public ResponseEntity deletePlaceFromPlaceList(@RequestHeader(value = "Authorization") String request, @PathVariable String placeListName, @PathVariable String placeId) {
        String username = tokenService.getUsername(request);
        return new ResponseEntity(userService.deletePlaceFromPlaceList(username, placeListName, placeId),HttpStatus.OK);
    }

    //Marcar lugar como visitado
    @RequestMapping(value = "/place_list/{placeListName}/place/{placeId}",method = RequestMethod.PATCH)
    public ResponseEntity markPlaceAsVisited(@RequestHeader(value = "Authorization") String request, @PathVariable String placeListName, @PathVariable String placeId){
        String username = tokenService.getUsername(request);
        return new ResponseEntity(userService.markPlaceAsVisited(username, placeListName, placeId), HttpStatus.OK);
    }
}
