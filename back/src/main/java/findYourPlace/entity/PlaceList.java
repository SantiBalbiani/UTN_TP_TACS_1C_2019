package findYourPlace.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import findYourPlace.entity.exception.ElementAlreadyExistsException;
import findYourPlace.entity.exception.ElementDoesNotExistException;

public class PlaceList {

    private String name;
    private List<Place> places;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PlaceList(@JsonProperty("name") String name) {
        this.name = name;
        this.places = new ArrayList<Place>();
    }

    public void removePlace(Place place){
       this.places.remove(place);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<Place> getPlaces() {
        return places;
    }

    public Place getPlaceByPlaceId(String id) throws ElementDoesNotExistException {
        for(Place place:places){
            if(place.getFortsquareId().equals(id)){
                return place;
            }
        }
        throw new ElementDoesNotExistException("Place with id "+id+" does not belong to list "+getName());
    }

    public void validatePlace(Place _place) throws ElementAlreadyExistsException {
        for(Place place:places){
            if(place.getFortsquareId().equals(_place.getFortsquareId())){
                throw new ElementAlreadyExistsException("Place with id "+_place.getFortsquareId()+" already belongs to list "+getName());
            }
        }
    }

    public void addPlace(Place place) throws ElementAlreadyExistsException {
        validatePlace(place);
        places.add(place);
    }

    public void removePlace(String id) {
        for (int x= 0 ; x < this.places.size() ; x++) {
            if (this.places.get(x).getFortsquareId().equals(id)) {
                this.places.remove(this.places.get(x));
            }
        }
    }

    public void removePlaceByPlaceId(String placeId) {
        Place place = getPlaceByPlaceId(placeId);
        removePlace(place);
    }

    public void setPlaces(java.util.List<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        if(this.places.size() == 0) {
            return "Lista vacia";
        }

        String description = "";
        for (Place place: this.places) {
            description += place.toString() + "\n";
        }

        return description;
    }

    public boolean isPlacePresent(Place place) {
        return places.stream().anyMatch(aPlace -> aPlace.getFortsquareId().equals(place.getFortsquareId()));
    }

    public boolean isPlaceIdPresent(int placeId) {
        return places.stream().anyMatch(aPlace -> aPlace.getFortsquareId().equals(placeId));
    }

    public Boolean markVisitedPlace(Place place) {
        return place.setVisited(true);
     }
}
