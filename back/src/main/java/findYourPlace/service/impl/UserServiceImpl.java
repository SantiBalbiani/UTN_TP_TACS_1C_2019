package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.mongoDB.Model;
import findYourPlace.mongoDB.UserDao;
import findYourPlace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public User getUser(String userId) {
        Optional<User> user = userDao.findById(userId);
        return user.get();
    }

    @Override
    public List<PlaceList> getUserPlaces(String userId) {
        return getUser(userId).getPlaceLists();
    }

    @Override
    public User createUserPlaces(String userId, PlaceList placeList) throws Exception {
        User user = getUser(userId);
        PlaceList existingPlaceList = user.findPlaceListByName(placeList.getName());
        if(existingPlaceList==null) {
            user.addPlaceToPlaceList(placeList);
            userDao.save(user);
            return user;
        } else {
            throw new Exception("El usuario "+user.getUsername()+" ya tiene una lista con el nombre "+placeList.getName());
        }
    }

    @Override
    public User deleteUserPlaces(String userId, int placeListId) {
        User user = getUser(userId);
        user.removePlaceList(placeListId);
        userDao.save(user);
        return user;
    }

    @Override
    public String modifyUserPlaces(String userId, int placeListId, String placeListName) {
        return model.modifyUserPlaceList(userId, placeListId, placeListName) ? "Lista modificada con éxito" : "Error modificando la lista";
    }

    public Place getPlace(long id) {
        return model.getPlace(id);
    }

}
