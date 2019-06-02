package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.mongoDB.Model;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.service.UserService;
import findYourPlace.service.impl.exception.CouldNotDeleteElementException;
import findYourPlace.service.impl.exception.CouldNotModifyElementException;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
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
        } catch (DuplicateKeyException ex){
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
        } catch (CouldNotRetrieveElementException ex){
            throw new CouldNotSaveElementException(ex.getMessage());
        }
    }

    @Override
    public User deleteUserPlaces(String userId, String placeListName) throws CouldNotDeleteElementException {
        try{
            User user = getUser(userId);
            user.removePlaceList(placeListName);
            userDao.save(user);
            return user;
        } catch (CouldNotRetrieveElementException ex){
            throw new CouldNotDeleteElementException(ex.getMessage());
        }
    }

    @Override
    public User modifyUserPlaces(String userId, String placeListCurrentName, String placeListNewName) throws CouldNotModifyElementException {
        try {
            User user = getUser(userId);
            user.modifyPlaceList(placeListCurrentName, placeListNewName);
            userDao.save(user);
            return user;
        } catch (CouldNotRetrieveElementException ex){
            throw new CouldNotModifyElementException(ex.getMessage());
        }
    }

    @Override
    public Place getPlace(long id) {
        return model.getPlace(id);
    }

    @Override
    public String markPlaceAsVisited(String userId, String placeListId, Place place) {
        return model.markPlaceAsVisited(userId, placeListId, place) ? "Lugar agregado con éxito" : "Error agregando lugar";
    }

}
