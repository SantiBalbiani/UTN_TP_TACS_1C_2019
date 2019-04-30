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
        return model.getUserPlaces(userId);
    }

    @Override
    public String createUserPlaces(String userId, PlaceList placeList) {
        return model.addUserPlaces(userId, placeList) ? "Lista creada con éxito" : "Error creando la lista";
    }

    @Override
    public String deleteUserPlaces(String userId, int placeListId) {
        return model.removeUserPlaceList(userId, placeListId) ? "Lista eliminada con éxito" : "Error eliminando la lista";
    }

    @Override
    public String modifyUserPlaces(String userId, int placeListId, String placeListName) {
        return model.modifyUserPlaceList(userId, placeListId, placeListName) ? "Lista modificada con éxito" : "Error modificando la lista";
    }

    public Place getPlace(long id) {
        return model.getPlace(id);
    }

}
