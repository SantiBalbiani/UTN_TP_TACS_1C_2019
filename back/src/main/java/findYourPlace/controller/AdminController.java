package findYourPlace.controller;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.PlaceService;
import findYourPlace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

    @RequestMapping("/list_comparator/{userId}/{userListPlaceIndex}/{userId2}/{userListPlaceIndex2}")
    public HashMap<String, List<Place>> listComparator(@PathVariable String userId, @PathVariable int userListPlaceIndex,
                                                       @PathVariable String userId2, @PathVariable int userListPlaceIndex2) {

        HashMap<String, List<Place>> response = new HashMap<>();

        User user = userService.getUser(userId);
        User user2 = userService.getUser(userId2);

        PlaceList placeList = user.getPlaceLists().get(userListPlaceIndex);
        PlaceList placeList2 = user2.getPlaceLists().get(userListPlaceIndex2);

        List<Place> commonPlaces = placeList.getPlaces().stream()
                .filter(aPlace -> placeList2.isPlacePresent(aPlace))
                .collect(Collectors.toList());

        response.put("commonPlaces", commonPlaces);

        return response;
    }

    @RequestMapping("/place/{placeId}/interested")
    public HashMap<String, Integer> interestedUsers(@PathVariable int placeId) {
        HashMap<String, Integer> response = new HashMap<>();
        
        List<User> interestedUsers = null;
        List<User> allUsers = userService.getUsers();

        for (int x = 0; x == allUsers.size(); x++) {
            if (allUsers.get(x).gotThisPlace(placeId)) {
                interestedUsers.add(allUsers.get(x));
            }
        }

        response.put("interestedUsers", allUsers.size());

        return response;
    }

    //Visualizar un usuario
    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable String userId) {
        return new ResponseEntity(userService.getUser(userId), HttpStatus.OK);
    }

    //Visualizar lugares
    @RequestMapping(value = "/places_registered_days_ago/{days_ago}",method = RequestMethod.GET)
    public ResponseEntity getPlacesRegisteredAtDaysAgo(@PathVariable Integer days_ago) {
        return new ResponseEntity(placeService.getPlacesRegisteredAtDaysAgo(days_ago), HttpStatus.OK);
    }

}
