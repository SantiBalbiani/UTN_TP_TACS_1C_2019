package findYourPlace.controller;

import findYourPlace.connector.FourSquareConnector;
import findYourPlace.entity.Place;
import findYourPlace.utils.PlaceDeserializer;

import findYourPlace.utils.PlacesResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/place")
public class PlaceController {
	
	private RestTemplate restTemplate;
	private PlaceDeserializer deserializer; 
	
	public PlaceController()
	{
		this.restTemplate = new RestTemplate();
		this.deserializer = new PlaceDeserializer();
	}

    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Place> getPlace(@RequestParam String description, @RequestParam(required = false) Float latitude, @RequestParam(required = false) Float longitude) {

    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(FourSquareConnector.getHost())
    	        .queryParam("query", description)
    	        .queryParam("ll", latitude + "," + longitude);
    	
    	
    	RestTemplate restTemplate = new RestTemplate();

		// ToDo: habria que buscar si existe una forma mas "prolija" de hacerlo y reemplazar el PlacesResponse.
    	ResponseEntity<PlacesResponse> response = restTemplate.exchange(
    			builder.toUriString(),
    	  		HttpMethod.GET,
				null,
    	  		new ParameterizedTypeReference<PlacesResponse>(){});
    	
    	List<Place> places = response.getBody().getPlaces();

    	return places;
  
    }
    
    
}
