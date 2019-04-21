package findYourPlace.service;

import findYourPlace.entity.User;
import findYourPlace.entity.PlaceList;
import findYourPlace.mongoDB.Model;

import java.util.List;

public class UserService {

    private static Model model = Model.getModel();

    public static String createUser(User user) {
        //TODO: modificar esto por la creación del usuario en mongo
        return model.addUser(user)?"Usuario creado con éxito":"Error creando usuario";
    }

    public static User getUser(long userId) {
        //TODO: modificar esto para que el usuario se recupere de la base en mongo
        return model.getUser(userId);
    }

    public static List<PlaceList> getUserPlaces(long userId) {
        return model.getUserPlaces(userId);
    }

    public static String createUserPlaces(int userId, PlaceList placeList) {
        return model.addUserPlaces(userId, placeList)?"Lista creada con éxito":"Error creando la lista";
    }

    public static String deleteUserPlaces(int userId, int placeListId) {
        return model.removeUserPlaceList(userId, placeListId)?"Lista eliminada con éxito":"Error eliminando la lista";
    }

    public static String modifyUserPlaces(int userId, int placeListId, String placeListName) {
        return model.modifyUserPlaceList(userId, placeListId, placeListName)?"Lista modificada con éxito":"Error modificando la lista";
    }
}
