package findYourPlace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import findYourPlace.entity.exception.ElementAlreadyExistsException;
import findYourPlace.entity.exception.ElementDoesNotExistException;
import findYourPlace.utils.Encrypt;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Document
public class User implements UserDetails{

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String role = "user";
    private List<PlaceList> placeLists;


    @PersistenceConstructor
    public User(
            @JsonProperty("username") String username,
            @JsonProperty("role") String role,
            @JsonProperty("password") String password){
    	this.username = username;
        this.role = role;
        this.password = password;
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
        this.password = password;
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
        for(Place place : placeList.getPlaces()){
            place.setListName(placeListName);
        }
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

	public boolean gotThisPlace(int placeId) {
        List<PlaceList> places = this.getPlaceLists();

        for (int x=0; x==places.size(); x++) {
            if (places.get(x).isPlaceIdPresent(placeId)) {
                return true;
            }
        }
        return false;
    }
    
    public int getPlaceListsCount() {
        return this.placeLists.size();
    }

    public int getVisitedPlaceCount() {
        List<PlaceList> places = this.getPlaceLists();
        int count = 0;

        for (int x=0; x<places.size(); x++) {

            List<Place> oneList = places.get(x).getPlaces();

            for (int y=0; y<oneList.size(); y++) {

                if (oneList.get(y).isVisited()) {
                    count++;
                };

            }
        }

        return count;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}