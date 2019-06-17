package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.entity.exception.ElementAlreadyExistsException;
import findYourPlace.entity.exception.ElementDoesNotExistException;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.service.UserService;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;
import findYourPlace.utils.Encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User createUser(User user) throws CouldNotSaveElementException {
        try {
        	user.setPassword(Encrypt.encrypt(user.getPassword()));
            return userDao.save(user);
        } catch (DuplicateKeyException ex){
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }

    @Override
    public User getUser(String userId) throws CouldNotRetrieveElementException {
        try {
            Optional<User> user = userDao.findById(userId);
            return user.get();
        } catch (NoSuchElementException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public void deleteUser(String userId) throws CouldNotRetrieveElementException {
        try{
            userDao.deleteById(userId);
        } catch (NoSuchElementException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public List<PlaceList> getUserPlaces(String userId) throws CouldNotRetrieveElementException {
        return getUser(userId).getPlaceLists();
    }

    @Override
    public User createUserPlaces(String userId, PlaceList placeList) throws CouldNotSaveElementException {
        try {
            User user = getUser(userId);
            user.createPlaceList(placeList);
            userDao.save(user);
            return user;
        } catch (ElementAlreadyExistsException ex){
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }

    @Override
    public  PlaceList getUserPlacesByName(String userId, String placeListName) throws  CouldNotRetrieveElementException {
        try{
            User user = getUser(userId);
            return user.findPlaceListByName(placeListName);
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public User deleteUserPlaces(String userId, String placeListName) throws CouldNotRetrieveElementException {
        try{
            User user = getUser(userId);
            user.removePlaceList(placeListName);
            userDao.save(user);
            return user;
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList modifyUserPlaces(String userId, String placeListCurrentName, String placeListNewName) throws CouldNotRetrieveElementException {
        try {
            User user = getUser(userId);
            user.modifyPlaceList(placeListCurrentName, placeListNewName);
            userDao.save(user);
            return user.findPlaceListByName(placeListNewName);
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList addPlaceToPlaceList(String userId, String placeListName, Place place) throws CouldNotRetrieveElementException{
        try {
            User user = getUser(userId);
            user.addPlaceToPlaceList(placeListName, place);
            userDao.save(user);
            return user.findPlaceListByName(placeListName);
        } catch (ElementDoesNotExistException ex) {
            //List does not exist
            throw new CouldNotRetrieveElementException(ex.getMessage());
        } catch (ElementAlreadyExistsException ex){
            //Element already belongs to list
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList addPlaceToPlaceList(String userId, String placeListName, String placeId) throws CouldNotRetrieveElementException{
        try {
            User user = getUser(userId);
            Place place = new Place(placeId);
            user.addPlaceToPlaceList(placeListName, place);
            userDao.save(user);
            return user.findPlaceListByName(placeListName);
        } catch (ElementDoesNotExistException ex) {
            //List does not exist
            throw new CouldNotRetrieveElementException(ex.getMessage());
        } catch (ElementAlreadyExistsException ex){
            //Element already belongs to list
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }

    @Override
    public Place getPlaceFromPlaceList(String userId, String placeListName, String placeId) throws CouldNotRetrieveElementException {
        try {
            User user = getUser(userId);
            return user.getPlaceFromPlaceList(placeListName, placeId);
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList deletePlaceFromPlaceList(String userId, String placeListName, String placeId) throws CouldNotRetrieveElementException {
        try {
            User user = getUser(userId);
            user.deletePlaceFromPlaceList(placeListName, placeId);
            userDao.save(user);
            return user.findPlaceListByName(placeListName);
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public Place markPlaceAsVisited(String userId, String placeListName, String placeId) 
            throws CouldNotRetrieveElementException {
        try {
            User user = getUser(userId);
            Place place = user.getPlaceFromPlaceList(placeListName, placeId);
            place.setVisited(true);
            userDao.save(user);
            return place;
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotRetrieveElementException(ex.getMessage());
        }
    }

    @Override
    public List<User> getUsers() throws CouldNotRetrieveElementException {
        try {
            List<User> users = userDao.findAll();
            return users;
        } catch (Exception e){
            throw new CouldNotRetrieveElementException(e.getMessage());
        }
    }

    @Override
    public List<Place> getLugaresRegistradosHoy() throws CouldNotRetrieveElementException {
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date dateBeforeDays = cal.getTime();
            List<User> users = userDao.findByLastModifiedGreaterThan(dateBeforeDays);
            List<Place> places = new ArrayList<Place>();
            for(User user:users){
                for(PlaceList placeList:user.getPlaceLists()){
                    for(Place place:placeList.getPlaces()){
                        if(place.getTimeStamp()!=null && place.getTimeStamp().after(dateBeforeDays)){
                            places.add(place);
                        }
                    }
                }
            }
            return places;
        } catch (Exception e) {
            throw new CouldNotRetrieveElementException(e.getMessage());
        }
    }


}
