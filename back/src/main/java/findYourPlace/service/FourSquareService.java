package findYourPlace.service;

import findYourPlace.entity.Place;

import java.util.List;

/**
 * Created by icernigoj on 23/04/2019.
 */
public interface FourSquareService {

    List<Place> searchPlaces(String description);
    List<Place> searchPlaces(String description, Float latitude, Float longitude);
}
