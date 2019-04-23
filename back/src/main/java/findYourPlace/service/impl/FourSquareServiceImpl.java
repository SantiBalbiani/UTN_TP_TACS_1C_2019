package findYourPlace.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import findYourPlace.entity.Place;
import findYourPlace.mongoDB.Model;
import findYourPlace.service.FourSquareService;
import findYourPlace.utils.PlacesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FourSquareServiceImpl implements FourSquareService {

    private static Model model = Model.getModel();

    @Value("${foursquare.client-id}")
    private String clientid;

    @Value("${foursquare.client-secret}")
    private String clientsecret;
	
    private String host = "https://api.foursquare.com/v2/venues/search?ll=40.7,-74&client_id=" + clientid + "&client_secret=" + clientsecret + "&v=" + "20192204";

    @Override
    public List<Place> searchPlaces(String description) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host)
                .queryParam("query", description);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PlacesResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PlacesResponse>(){});

        List<Place> places = response.getBody().getPlaces();

        for (Place place: places) {
            model.savePlace(place);
        }
        return places;
    }

    @Override
    public List<Place> searchPlaces(String description, Float latitude, Float longitude) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host)
                .queryParam("query", description)
                .queryParam("ll", latitude + "," + longitude);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PlacesResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PlacesResponse>(){});

        List<Place> places = response.getBody().getPlaces();

        for (Place place: places) {
            model.savePlace(place);
        }

        return places;
    }

}
