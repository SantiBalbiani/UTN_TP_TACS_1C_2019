package findYourPlace.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

public class PlaceList {

    private String name;
    private List<Place> places;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PlaceList(@JsonProperty("name") String name) {
        this.name = name;
        this.places = new ArrayList<Place>();
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

    public void addPlace(Place place) {
        if(!this.places.contains(place)) {
            this.places.add(place);
        }
    }

    public void removePlace(long id) {
        for (int x= 0 ; x < this.places.size() ; x++) {
            if (this.places.get(x).getId() == id) {
                this.places.remove(this.places.get(x));
            }
        }
    }

    public void setPlaces(java.util.List<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        String description = "";
        for (Place place: this.places) {
            description += place.toString() + "\n";
        }

        return description;
    }

    public boolean isPlacePresent(Place place) {
        return places.stream().anyMatch(aPlace -> aPlace.getPlaceId().equals(place.getPlaceId()));
    }
}
