package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.entity.exception.ElementAlreadyExistsException;
import findYourPlace.entity.exception.ElementDoesNotExistException;
import findYourPlace.mongoDB.Model;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.service.UserService;
import findYourPlace.service.impl.exception.CouldNotDeleteElementException;
import findYourPlace.service.impl.exception.CouldNotModifyElementException;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static Model model = Model.getModel();
    @Autowired
    private UserDao userDao;

    @Override
    public User createUser(User user) throws CouldNotSaveElementException {
        try {
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
    public void deleteUser(String userId) throws CouldNotDeleteElementException {
        try{
            userDao.deleteById(userId);
        } catch (NoSuchElementException ex){
            throw new CouldNotDeleteElementException(ex.getMessage());
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
    public User deleteUserPlaces(String userId, String placeListName) throws CouldNotDeleteElementException {
        try{
            User user = getUser(userId);
            user.removePlaceList(placeListName);
            userDao.save(user);
            return user;
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotDeleteElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList modifyUserPlaces(String userId, String placeListCurrentName, String placeListNewName) throws CouldNotModifyElementException {
        try {
            User user = getUser(userId);
            user.modifyPlaceList(placeListCurrentName, placeListNewName);
            userDao.save(user);
            return user.findPlaceListByName(placeListNewName);
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotModifyElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList addPlaceToPlaceList(String userId, String placeListName, Place place) throws CouldNotModifyElementException{
        try {
            User user = getUser(userId);
            user.addPlaceToPlaceList(placeListName, place);
            userDao.save(user);
            return user.findPlaceListByName(placeListName);
        } catch (ElementDoesNotExistException ex) {
            //List does not exist
            throw new CouldNotModifyElementException(ex.getMessage());
        } catch (ElementAlreadyExistsException ex){
            //Element already belongs to list
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }

    @Override
    public PlaceList addPlaceToPlaceList(String userId, String placeListName, String placeId) throws CouldNotModifyElementException{
        try {
            User user = getUser(userId);
            Place place = new Place(placeId);
            user.addPlaceToPlaceList(placeListName, place);
            userDao.save(user);
            return user.findPlaceListByName(placeListName);
        } catch (ElementDoesNotExistException ex) {
            //List does not exist
            throw new CouldNotModifyElementException(ex.getMessage());
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
    public PlaceList deletePlaceFromPlaceList(String userId, String placeListName, String placeId) throws CouldNotDeleteElementException {
        try {
            User user = getUser(userId);
            user.deletePlaceFromPlaceList(placeListName, placeId);
            userDao.save(user);
            return user.findPlaceListByName(placeListName);
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotDeleteElementException(ex.getMessage());
        }
    }

    @Override
    public Place markPlaceAsVisited(String userId, String placeListName, String placeId) {
        try {
            User user = getUser(userId);
            Place place = user.getPlaceFromPlaceList(placeListName, placeId);
            place.setVisited(true);
            userDao.save(user);
            return place;
        } catch (ElementDoesNotExistException ex){
            throw new CouldNotModifyElementException(ex.getMessage());
        }
    }

}
