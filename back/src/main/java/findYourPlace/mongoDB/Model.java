package findYourPlace.mongoDB;

import findYourPlace.entity.Place;
import findYourPlace.entity.PlaceList;
import findYourPlace.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model model;
    private List<User> users;
    private List<Place> places;

    public static Model getModel() {
        if (model == null) {
            model = new Model();
        }

        return model;
    }

    private Model() {
        this.users = new ArrayList<User>();
        this.places = new ArrayList<Place>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean addUser(User user) {
        return this.users.add(user);
    }

    public User getUser(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId))
                return user;
        }
        return null;
    }

    public List<PlaceList> getUserPlaces(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId))
                return user.getPlaceLists();
        }
        return null;
    }

    public List<PlaceList> setUserPlaces(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId))
                return user.getPlaceLists();
        }
        return null;
    }

    public boolean addUserPlaces(String userId, PlaceList placeList) {
        for (int x = 0; x < users.size(); x++) {
            if (users.get(x).getId().equals(userId)) {
                try {
                    return this.users.get(x).addPlaceToPlaceList(placeList);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean removeUserPlaceList(String userId, int placeListId) {
        for (int x = 0; x < users.size(); x++) {
            if (users.get(x).getId().equals(userId)) {
                try {
                    return this.users.get(x).removePlaceList(this.users.get(x).findPlaceList(placeListId));
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean modifyUserPlaceList(String userId, int placeListId, String placeListName) {
        for (int x = 0; x < users.size(); x++) {
            if (users.get(x).getId().equals(userId)) {
                try {
                    return this.users.get(x).modifyPlaceList(this.users.get(x).findPlaceList(placeListId), placeListName);
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return false;
    }

    public void savePlace(Place place) {
        places.add(place);
    }

    public List<Place> getPlaces() {
        return places;
    }

    public Place getPlace(long id) {
        for (Place place: this.places) {
            if(place.getId() == id) {
                return place;
            }
        }
        return null;
    }
}
