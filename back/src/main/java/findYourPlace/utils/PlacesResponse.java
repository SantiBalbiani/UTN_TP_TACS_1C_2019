package findYourPlace.utils;

import findYourPlace.entity.Place;

import java.util.HashMap;
import java.util.List;

public class PlacesResponse {

    private HashMap<String, List<Place>> response;

    // ToDo: pensar en refactorizar esto. No se si es la mejor forma de hacerlo
    public List<Place> getPlaces() {
        if (this.getResponse() != null) {
            return (List<Place>) this.getResponse().get("venues");
        }
        return null;
    }

    public HashMap<String, List<Place>> getResponse() {
        return this.response;
    }
}
