package findYourPlace.controller;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    @Autowired
    private UserService userService;

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
