package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.mongoDB.Model;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.service.UserService;
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
    public User createUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUser(String userId) throws NoSuchElementException {
        Optional<User> user = userDao.findById(userId);
        return user.get();
    }

    @Override
    public void deleteUser(String userId) throws NoSuchElementException {
        userDao.deleteById(userId);
    }

    @Override
    public List<PlaceList> getUserPlaces(String userId) {
        return getUser(userId).getPlaceLists();
    }

    @Override
    public User createUserPlaces(String userId, PlaceList placeList) throws DuplicateKeyException {
        User user = getUser(userId);
        user.createPlaceList(placeList);
        userDao.save(user);
        user = getUser(userId);
        return user;
    }

    @Override
    public User deleteUserPlaces(String userId, String placeListName) throws NoSuchElementException {
        User user = getUser(userId);
        user.removePlaceList(placeListName);
        userDao.save(user);
        user = getUser(userId);
        return user;
    }

    @Override
    public User modifyUserPlaces(String userId, String placeListCurrentName, String placeListName) {
        User user = getUser(userId);
        user.modifyPlaceList(placeListCurrentName,placeListName);
        userDao.save(user);
        user = getUser(userId);
        return user;
    }

    public Place getPlace(long id) {
        return model.getPlace(id);
    }

}
