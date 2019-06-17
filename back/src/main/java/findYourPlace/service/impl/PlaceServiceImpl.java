package findYourPlace.service.impl;

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
    public List<Place> getPlacesRegisteredAtDaysAgo(Integer daysAgo) throws CouldNotRetrieveElementException {
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -daysAgo);
            Date dateBeforeDays = cal.getTime();
            List<Place> places = placeDao.findByAddedAtGreaterThan(dateBeforeDays);
            return places;
        } catch (Exception e) {
            throw new CouldNotRetrieveElementException(e.getMessage());
        }
    }

    @Override
    public List<Place> getPlacesRegisteredSinceBeginingOfTime() throws CouldNotRetrieveElementException {
        try {
            Date dateBeforeDays = new Date();
            dateBeforeDays.setTime(0);
            List<Place> places = placeDao.findByAddedAtGreaterThan(dateBeforeDays);
            return places;
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
            Place placeBD = placeDao.findById(place.getFortsquareId()).get();
            placeBD.setVisited(place.getVisited());
            placeDao.save(placeBD);
            return placeBD;
        } catch(NoSuchElementException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public void deleteById(String id) throws CouldNotRetrieveElementException{
        try {
            Place place = placeDao.findById(id).get();
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
