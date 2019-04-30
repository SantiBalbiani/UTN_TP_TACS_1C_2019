package findYourPlace.service;

import findYourPlace.entity.Place;
import findYourPlace.entity.User;
import findYourPlace.entity.PlaceList;
import findYourPlace.mongoDB.Model;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUser(long userId);

    List<PlaceList> getUserPlaces(long userId);

    String createUserPlaces(int userId, PlaceList placeList);

    String deleteUserPlaces(int userId, int placeListId);

    String modifyUserPlaces(int userId, int placeListId, String placeListName);

    Place getPlace(long id);
}
