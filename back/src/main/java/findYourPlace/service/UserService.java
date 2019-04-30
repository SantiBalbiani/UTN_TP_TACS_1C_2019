package findYourPlace.service;

import findYourPlace.entity.Place;
import findYourPlace.entity.User;
import findYourPlace.entity.PlaceList;
import findYourPlace.mongoDB.Model;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUser(String userId);

    List<PlaceList> getUserPlaces(String userId);

    String createUserPlaces(String userId, PlaceList placeList);

    String deleteUserPlaces(String userId, int placeListId);

    String modifyUserPlaces(String userId, int placeListId, String placeListName);

    Place getPlace(long id);
}
