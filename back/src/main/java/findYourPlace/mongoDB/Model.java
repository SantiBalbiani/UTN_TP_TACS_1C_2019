package findYourPlace.mongoDB;

import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;

import java.util.ArrayList;
import java.util.List;

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

    public List<PlaceList> getUserPlaces(long userId){
        for(User user:users){
            if(user.getId()==userId)
                return user.getPlaceLists();
        }
        return null;
    }

    public List<PlaceList> setUserPlaces(long userId){
        for(User user:users){
            if(user.getId()==userId)
                return user.getPlaceLists();
        }
        return null;
    }

    public boolean addUserPlaces(int userId, PlaceList placeList){
        for (int x=0; x < users.size(); x++) {
            if (users.get(x).getId() == userId) {
                try{
                    return this.users.get(x).addPlaceToPlaceList(placeList);
                } catch (Exception e){
                    return false;
                }
            }
        }
        return false;
    }

    public boolean removeUserPlaceList(int userId, int placeListId) {
        for (int x=0; x < users.size(); x++) {
            if (users.get(x).getId() == userId) {
                try{
                    return this.users.get(x).removePlaceList(this.users.get(x).findPlaceList(placeListId));
                } catch (Exception e){
                    return false;
                }
            }
        }
        return false;
    }

    public boolean modifyUserPlaceList(int userId, int placeListId, String placeListName) {
        for (int x=0; x < users.size(); x++) {
            if (users.get(x).getId() == userId) {
                try{
                    return this.users.get(x).modifyPlaceList(this.users.get(x).findPlaceList(placeListId), placeListName);
                } catch (Exception e){
                    return false;
                }
            }
        }
        return false;
    }
}
