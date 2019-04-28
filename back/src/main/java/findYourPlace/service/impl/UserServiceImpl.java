package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.mongoDB.Model;
import findYourPlace.mongoDB.UserRepository;
import findYourPlace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static Model model = Model.getModel();
    @Autowired
    private UserRepository userRepository;

    @Override
    public String createUser(User user) {
        userRepository.save(user);
        return  "Usuario creado con éxito";
    }

    @Override
    public User getUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }

    @Override
    public List<PlaceList> getUserPlaces(long userId) {
        return model.getUserPlaces(userId);
    }

    @Override
    public String createUserPlaces(int userId, PlaceList placeList) {
        return model.addUserPlaces(userId, placeList) ? "Lista creada con éxito" : "Error creando la lista";
    }

    @Override
    public String deleteUserPlaces(int userId, int placeListId) {
        return model.removeUserPlaceList(userId, placeListId) ? "Lista eliminada con éxito" : "Error eliminando la lista";
    }

    @Override
    public String modifyUserPlaces(int userId, int placeListId, String placeListName) {
        return model.modifyUserPlaceList(userId, placeListId, placeListName) ? "Lista modificada con éxito" : "Error modificando la lista";
    }

    public Place getPlace(long id) {
        return model.getPlace(id);
    }

}
