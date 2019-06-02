package findYourPlace.service;

import findYourPlace.entity.Place;
import findYourPlace.entity.User;
import findYourPlace.entity.PlaceList;
import findYourPlace.mongoDB.Model;
import findYourPlace.service.impl.exception.CouldNotDeleteElementException;
import findYourPlace.service.impl.exception.CouldNotModifyElementException;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.NoSuchElementException;

public interface UserService {

    User createUser(User user) throws CouldNotSaveElementException;

    void deleteUser(String userId) throws CouldNotDeleteElementException;

    User getUser(String userId) throws CouldNotRetrieveElementException;

    List<PlaceList> getUserPlaces(String userId) throws CouldNotRetrieveElementException;

    User createUserPlaces(String userId, PlaceList placeList) throws CouldNotSaveElementException;

    User deleteUserPlaces(String userId, String placeListName) throws CouldNotDeleteElementException;

    User modifyUserPlaces(String userId, String placeListCurrentName, String placeListNewName) throws CouldNotModifyElementException;

    Place getPlace(long id);

    String markPlaceAsVisited(String userId, String placeListId, Place place);
}
