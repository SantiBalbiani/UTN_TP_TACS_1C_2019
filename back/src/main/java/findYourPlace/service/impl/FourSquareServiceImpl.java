package findYourPlace.service.impl;

import findYourPlace.entity.Place;
import findYourPlace.service.FourSquareService;
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

    private String getHostUrl() {
        return "https://api.foursquare.com/v2/venues/search?" +
                "client_id=" + clientid + "&client_secret=" + clientsecret + "&v=" + version + "&ll=" + buenosAiresLL;
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
