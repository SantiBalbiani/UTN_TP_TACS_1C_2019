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
        long id = (Long) ((LongNode) node.get("id")).numberValue();
        String placeName = node.get("name").asText();
 
        return new Place(placeName, id);
    }


}