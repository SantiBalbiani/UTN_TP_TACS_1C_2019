package findYourPlace.service;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;

import java.util.List;

public interface UserService {

    User createUser(User user) throws CouldNotSaveElementException;

    void deleteUser(String userName) throws CouldNotRetrieveElementException;

    User getUser(String userId) throws CouldNotRetrieveElementException;

    User getUserByUsername(String username) throws CouldNotRetrieveElementException;

    List<PlaceList> getUserPlaces(String username) throws CouldNotRetrieveElementException;

    User createUserPlaces(String username, PlaceList placeList) throws  CouldNotSaveElementException;

    PlaceList getUserPlacesByName(String username, String placeListName) throws  CouldNotRetrieveElementException;

    User deleteUserPlaces(String username, String placeListName) throws CouldNotRetrieveElementException;

    PlaceList modifyUserPlaces(String username, String placeListCurrentName, String placeListNewName) throws CouldNotRetrieveElementException;

    PlaceList addPlaceToPlaceList(String username, String placeListName, Place place) throws CouldNotRetrieveElementException;

    PlaceList addPlaceToPlaceList(String userId, String placeListName, String placeId) throws CouldNotRetrieveElementException;

    Place getPlaceFromPlaceList(String username, String placeListName, String placeId) throws CouldNotRetrieveElementException ;

    PlaceList deletePlaceFromPlaceList(String username, String placeListName, String placeId) throws CouldNotRetrieveElementException;

    Place markPlaceAsVisited(String username, String placeListName, String placeId) throws CouldNotRetrieveElementException;

	List<User> getUsers() throws CouldNotRetrieveElementException;

}
