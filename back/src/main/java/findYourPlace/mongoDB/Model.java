package findYourPlace.mongoDB;


import findYourPlace.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {

    private static Model model;
    private List<User> users;


    public static Model getModel(){
        if(model == null){
            model = new Model();
            model.setUsers(new ArrayList<User>());
        }
        return model;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean addUser(User user){
        try{
            return this.users.add(user);
        } catch (Exception e){
            return false;
        }
    }

    public User getUser(long userId){
        for(User user:users){
            if(user.getId()==userId)
                return user;
        }
        return null;
    }
}
