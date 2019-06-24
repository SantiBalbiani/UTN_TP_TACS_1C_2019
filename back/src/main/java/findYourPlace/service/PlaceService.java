package findYourPlace.service;

import findYourPlace.entity.AdminResponse;
import findYourPlace.entity.Place;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;

public interface PlaceService {

    AdminResponse getPlacesRegisteredAtDaysAgo(Integer daysAgo) throws CouldNotRetrieveElementException ;
    Place findById(String id) throws CouldNotRetrieveElementException;
    Place updatePlace(Place place) throws CouldNotRetrieveElementException;
    void deleteByComposedIndex(String fortsquareId,String userId,String listName) throws CouldNotRetrieveElementException;
    Place save(Place place) throws CouldNotSaveElementException;
    AdminResponse getPlaces() throws CouldNotRetrieveElementException;
}
