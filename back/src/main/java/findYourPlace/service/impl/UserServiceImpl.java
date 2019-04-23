package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;
import findYourPlace.mongoDB.Model;
import findYourPlace.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static Model model = Model.getModel();

    @Override
    public String createUser(User user) {
        //TODO: modificar esto por la creación del usuario en mongo
        return model.addUser(user) ? "Usuario creado con éxito" : "Error creando usuario";
    }

    @Override
    public User getUser(long userId) {
        //TODO: modificar esto para que el usuario se recupere de la base en mongo
        return model.getUser(userId);
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
