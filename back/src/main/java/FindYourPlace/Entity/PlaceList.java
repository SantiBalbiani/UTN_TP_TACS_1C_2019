package FindYourPlace.entity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class PlaceList {

    private final long id;
    private String name;
    private User user;
    private java.util.List<Place> places;

    static private final AtomicLong counter = new AtomicLong();

    public PlaceList(String name, User user) {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.user = user;
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

    public void removePlace(Place place) {
        this.places.remove(place);
    }

    public void setPlaces(java.util.List<Place> places) {
        this.places = places;
    }
}
