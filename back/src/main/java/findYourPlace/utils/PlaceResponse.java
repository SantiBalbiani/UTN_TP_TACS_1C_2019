package findYourPlace.utils;

import findYourPlace.entity.Place;

import java.util.HashMap;
import java.util.List;

public class PlaceResponse {

    private HashMap<String, Place> response;

    public Place getPlace() {
        if (this.getResponse() != null) {
            return (Place) this.getResponse().get("venue");
        }
        return null;
    }

    public HashMap<String, Place> getResponse() {
        return this.response;
    }
}
