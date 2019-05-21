package findYourPlace.entity;

import findYourPlace.utils.Encrypt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

    @PersistenceConstructor
    public User(
            @JsonProperty("username") String username,
            @JsonProperty("role") String role){
        this.username = username;
        this.role = role;
        this.placeLists = new ArrayList<PlaceList>();
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

    private PlaceList findPlaceListByNameNE(String placeListName) {
        for (PlaceList placelist:this.placeLists) {
            if(placeListName.equals(placelist.getName())) {
                return placelist;
            }
        }
        return null;
    }

    public PlaceList findPlaceListByName(String placeListName) throws NoSuchElementException {
        PlaceList placeList = findPlaceListByNameNE(placeListName);
        if(placeList!=null)
            return placeList;
        else
            throw new NoSuchElementException();
    }

    public void createPlaceList(PlaceList placeList){
        PlaceList placeListWithName = findPlaceListByNameNE(placeList.getName());
        if (placeListWithName==null)
            this.placeLists.add(placeList);
        else
            throw new DuplicateKeyException("");
    }

    public void modifyPlaceList(String placeListCurrentName,String placeListName){
        PlaceList placeList = findPlaceListByName(placeListCurrentName);
        placeList.setName(placeListName);
    }

    public boolean removePlaceList(String placeListName) throws NoSuchElementException {
        PlaceList placeList = findPlaceListByName(placeListName);
        this.placeLists.remove(placeList);
        return true;
    }


}
