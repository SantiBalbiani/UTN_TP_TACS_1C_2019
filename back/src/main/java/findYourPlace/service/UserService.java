package findYourPlace.service;

import findYourPlace.entity.User;
import findYourPlace.mongoDB.Model;

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
}
