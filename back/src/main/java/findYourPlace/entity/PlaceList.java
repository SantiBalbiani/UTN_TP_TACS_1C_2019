package findYourPlace.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceList {

    private final long id;
    private String name;
    private User user;
    private List<Place> places;

    static private final AtomicLong counter = new AtomicLong();
/*
    public PlaceList(String name, User user) {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.user = user;
        this.places = new ArrayList<Place>();
    }
*/
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PlaceList(@JsonProperty("name") String name) {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.user = null;
        this.places = new ArrayList<Place>(); 
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.util.List<Place> getPlaces() {
        return places;
    }

    public void addPlace(Place place) {
        if(!this.places.contains(place)) {
            this.places.add(place);
        }
    }
/*
    public void removePlace(Place place) {
        this.places.remove(place);
    }
*/

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
}
