package findYourPlace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import findYourPlace.entity.exception.ElementAlreadyExistsException;
import findYourPlace.entity.exception.ElementDoesNotExistException;
import findYourPlace.utils.Encrypt;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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

    public PlaceList findPlaceListByName(String placeListName) throws ElementDoesNotExistException {
        for (PlaceList placelist:this.placeLists) {
            if(placeListName.equals(placelist.getName())) {
                return placelist;
            }
        }
        throw new ElementDoesNotExistException("Place "+placeListName+" does not exist");
    }

    private void validatePlaceListName(String placeListName) throws ElementAlreadyExistsException {
        for (PlaceList placelist:this.placeLists) {
            if(placeListName.equals(placelist.getName())) {
                throw new ElementAlreadyExistsException("There's already a list named "+placelist.getName());
            }
        }
    }

    public void createPlaceList(PlaceList placeList) throws ElementAlreadyExistsException {
        validatePlaceListName(placeList.getName());
        this.placeLists.add(placeList);
    }

    public void modifyPlaceList(String placeListCurrentName,String placeListName) throws ElementDoesNotExistException {
        PlaceList placeList = findPlaceListByName(placeListCurrentName);
        placeList.setName(placeListName);
    }

    public void removePlaceList(String placeListName) throws ElementDoesNotExistException {
        PlaceList placeList = findPlaceListByName(placeListName);
        this.placeLists.remove(placeList);
    }

    public void addPlaceToPlaceList(String placeListName, Place place) throws ElementAlreadyExistsException {
        PlaceList placeList = findPlaceListByName(placeListName);
        placeList.addPlace(place);
    }

    public Place getPlaceFromPlaceList(String placeListName,String placeId) throws ElementDoesNotExistException {
        PlaceList placeList = findPlaceListByName(placeListName);
        return placeList.getPlaceByPlaceId(placeId);
    }

    public void deletePlaceFromPlaceList(String placeListName,String placeId) throws ElementDoesNotExistException {
        PlaceList placeList = findPlaceListByName(placeListName);
        Place place = getPlaceFromPlaceList(placeListName,placeId);
        placeList.removePlace(place);
    }
}
