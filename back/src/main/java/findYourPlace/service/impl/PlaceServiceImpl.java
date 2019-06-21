package findYourPlace.service.impl;

import findYourPlace.entity.AdminResponse;
import findYourPlace.entity.Place;
import findYourPlace.mongoDB.PlaceDao;
import findYourPlace.service.PlaceService;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceDao placeDao;


    @Override
    public AdminResponse getPlacesRegisteredAtDaysAgo(Integer daysAgo) throws CouldNotRetrieveElementException {
        try {
            Date dateBeforeDays;
            AdminResponse adminResponse = new AdminResponse();
            if(daysAgo > 0) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -daysAgo);
                dateBeforeDays = cal.getTime();
                adminResponse.setSinceDaysAgo(String.valueOf(daysAgo));
            } else {
                dateBeforeDays = new Date();
                dateBeforeDays.setTime(0);
                adminResponse.setSinceDaysAgo("The begining of time");
            }
            List<Place> places = placeDao.findByAddedAtGreaterThan(dateBeforeDays);
            adminResponse.setAddedPlaces(places.size());
            return adminResponse;
        } catch (Exception e) {
            throw new CouldNotRetrieveElementException(e.getMessage());
        }
    }

    @Override
    public Place findById(String id) throws CouldNotRetrieveElementException{
        try {
            return placeDao.findById(id).get();
        } catch(NoSuchElementException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public Place updatePlace(Place place) throws CouldNotRetrieveElementException{
        try {
            Place placeBD = placeDao.findByFortsquareIdAndUserIdAndListName(place.getFortsquareId(),place.getUserId(),place.getListName());
            placeBD.setVisited(place.getVisited());
            placeDao.save(placeBD);
            return placeBD;
        } catch(NoSuchElementException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public void deleteByComposedIndex(String fortsquareId,String userId,String listName) throws CouldNotRetrieveElementException{
        try {
            Place place = placeDao.findByFortsquareIdAndUserIdAndListName(fortsquareId,userId,listName);
            placeDao.delete(place);
        } catch (NoSuchElementException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public Place save(Place place) throws CouldNotSaveElementException{
        try {
            return placeDao.save(place);
        } catch (DuplicateKeyException ex){
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }


}
