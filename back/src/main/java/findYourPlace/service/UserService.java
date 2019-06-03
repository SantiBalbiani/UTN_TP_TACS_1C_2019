package findYourPlace.service;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.impl.exception.CouldNotDeleteElementException;
import findYourPlace.service.impl.exception.CouldNotModifyElementException;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;

import java.util.List;

public interface UserService {

    User createUser(User user) throws CouldNotSaveElementException;

    void deleteUser(String userId) throws CouldNotDeleteElementException;

    User getUser(String userId) throws CouldNotRetrieveElementException;

    List<PlaceList> getUserPlaces(String userId) throws CouldNotRetrieveElementException;

    User createUserPlaces(String userId, PlaceList placeList) throws  CouldNotSaveElementException;

    PlaceList getUserPlacesByName(String userId, String placeListName) throws  CouldNotRetrieveElementException;

    User deleteUserPlaces(String userId, String placeListName) throws CouldNotDeleteElementException;

    PlaceList modifyUserPlaces(String userId, String placeListCurrentName, String placeListNewName) throws CouldNotModifyElementException;

    PlaceList addPlaceToPlaceList(String userId, String placeListName, Place place) throws CouldNotModifyElementException;

    PlaceList addPlaceToPlaceList(String userId, String placeListName, String placeId) throws CouldNotModifyElementException;

    Place getPlaceFromPlaceList(String userId, String placeListName, String placeId) throws CouldNotRetrieveElementException ;

    PlaceList deletePlaceFromPlaceList(String userId, String placeListName, String placeId) throws CouldNotDeleteElementException;

    Place markPlaceAsVisited(String userId, String placeListId, String placeId) throws CouldNotModifyElementException;
}
