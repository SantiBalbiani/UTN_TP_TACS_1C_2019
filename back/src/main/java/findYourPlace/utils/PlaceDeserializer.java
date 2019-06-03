package findYourPlace.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;

import findYourPlace.entity.Place;

public class PlaceDeserializer extends StdDeserializer<Place> { 
	 
    public PlaceDeserializer() { 
        this(null); 
    } 
 
    public PlaceDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public Place deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String placeId = getStringNodeAttribute(node, "placeId");
        String name = getStringNodeAttribute(node, "name");
        JsonNode location = (node != null) ? node.get("location") : null;
        String address = getStringNodeAttribute(location, "address");
        Float lat = getFloatNodeAttribute(location, "latitude");
        Float lng = getFloatNodeAttribute(location, "longitude");
        String postalCode = getStringNodeAttribute(location, "postalCode");
        String cc = getStringNodeAttribute(location, "cc");
        String city = getStringNodeAttribute(location, "city");
        String state = getStringNodeAttribute(location, "state");
        String country = getStringNodeAttribute(location, "country");

        return new Place(placeId, name, address, lat, lng, postalCode, cc, city, state, country);
    }

    private String getStringNodeAttribute(JsonNode node, String attr) {
        return (node != null && node.get(attr) != null) ? node.get(attr).asText() : "";
    }

    private float getFloatNodeAttribute(JsonNode node, String attr) {
        return (node != null && node.get(attr) != null) ? node.get(attr).floatValue() : 0;
    }


}