package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.service.FourSquareService;
import findYourPlace.utils.PlaceResponse;
import findYourPlace.utils.PlacesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class FourSquareServiceImpl implements FourSquareService {

    @Value("${foursquare.client-id}")
    private String clientid;

    @Value("${foursquare.client-secret}")
    private String clientsecret;

    private String version = "20192204";

    private String buenosAiresLL = "-34.6161063,-58.480677";
    private String fourtsquareUrl = "https://api.foursquare.com/v2/venues";

    private String getHostUrl() {
        return fourtsquareUrl+"/search?" +
                "client_id=" + clientid + "&client_secret=" + clientsecret + "&v=" + version + "&ll=" + buenosAiresLL;
    }

    @Override
    public Place getPlaceById(String fourtsquareId){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fourtsquareUrl+"/"+fourtsquareId)
                .queryParam("client_id", clientid)
                .queryParam("client_secret",clientsecret)
                .queryParam("v",version);

        String uriString = builder.toUriString();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PlaceResponse> response = restTemplate.exchange(
                uriString,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PlaceResponse>(){});

        Place place = response.getBody().getPlace();

        return place;
    }

    @Override
    public List<Place> searchPlaces(String description) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getHostUrl())
                .queryParam("query", description);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PlacesResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PlacesResponse>(){});

        List<Place> places = response.getBody().getPlaces();

        return places;
    }

    @Override
    public List<Place> searchPlaces(String description, Float latitude, Float longitude) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getHostUrl())
                .queryParam("query", description)
                .queryParam("ll", latitude + "," + longitude);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PlacesResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PlacesResponse>(){});

        List<Place> places = response.getBody().getPlaces();

        return places;
    }

}
