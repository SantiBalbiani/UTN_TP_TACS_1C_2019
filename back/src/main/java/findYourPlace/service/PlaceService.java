package findYourPlace.service;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;

import java.util.List;

public interface PlaceService {

    List<Place> getPlacesRegisteredAtDaysAgo(Integer daysAgo) throws CouldNotRetrieveElementException ;
    List<Place> getPlacesRegisteredSinceBeginingOfTime() throws CouldNotRetrieveElementException;
    Place findById(String id) throws CouldNotRetrieveElementException;
    Place updatePlace(Place place) throws CouldNotRetrieveElementException;
    void deleteById(String id) throws CouldNotRetrieveElementException;
    Place save(Place place) throws CouldNotSaveElementException;
}
