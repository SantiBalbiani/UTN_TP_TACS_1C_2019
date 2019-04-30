package findYourPlace.entity;

import findYourPlace.utils.Encrypt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static findYourPlace.utils.Encrypt.salt;

@Document
public class User {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String role;
    private List<PlaceList> placeLists;

    static private final AtomicLong counter = new AtomicLong();

    @PersistenceConstructor
    public User(
            @JsonProperty("username") String username,
            @JsonProperty("role") String role,
            @JsonProperty("placeLists") List<PlaceList> placeLists){
        this.username = username;
        this.role = role;
        this.placeLists = placeLists;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Encrypt.get_SHA_256_SecurePassword(password, salt);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PlaceList> getPlaceLists() {
        return placeLists;
    }

    public void setPlaceLists(List<PlaceList> placeLists) {
        this.placeLists = placeLists;
    }

    public boolean addPlaceToPlaceList(PlaceList placeList) {
        this.placeLists.add(placeList);
        return true;
    }

    public PlaceList findPlaceList(int placeListId) {
        for (int x=0; x < this.placeLists.size(); x++) {
            if(this.placeLists.get(x).getId() == placeListId) {
                return this.placeLists.get(x);
            }
        }
        return null;
    }

    public PlaceList findPlaceListByName(String placeListName) {
        for (int x=0; x < this.placeLists.size(); x++) {
            if(this.placeLists.get(x).getName().equals(placeListName)) {
                return this.placeLists.get(x);
            }
        }
        return null;
    }


    public boolean removePlaceList(PlaceList placeList) {
        this.placeLists.remove(placeList);
        return true;
    }

    public boolean modifyPlaceList(PlaceList placeList, String placeListName) {
        for (int x=0; x < this.placeLists.size(); x++) {
            if (this.placeLists.get(x) == placeList) {
                this.placeLists.get(x).setName(placeListName);
                return true;
            }
        }
        return false;
    }
}
