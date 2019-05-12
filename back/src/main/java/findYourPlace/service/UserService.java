package findYourPlace.service;

import findYourPlace.entity.Place;
import findYourPlace.entity.User;
import findYourPlace.entity.PlaceList;
import findYourPlace.mongoDB.Model;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.NoSuchElementException;

public interface UserService {

    User createUser(User user);

    void deleteUser(String userId) throws NoSuchElementException;

    User getUser(String userId) throws NoSuchElementException;

    List<PlaceList> getUserPlaces(String userId);

    User createUserPlaces(String userId, PlaceList placeList) throws DuplicateKeyException;

    User deleteUserPlaces(String userId, int placeListId) throws NoSuchElementException;

    String modifyUserPlaces(String userId, int placeListId, String placeListName);

    Place getPlace(long id);
}
