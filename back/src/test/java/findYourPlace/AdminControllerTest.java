package findYourPlace;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.core.IsEqual;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import findYourPlace.security.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.IOException;
import java.util.Date;

/**
 * Created by icernigoj on 11/05/2019.
 */

public class AdminControllerTest {

    @Value("${server.port}")
    private String serverPort;

    private String serverAddress = "http://springboot";

    private String getApiUrl() {
        return serverAddress + ":" + 8080 + "/admin";
    }

    //Se crea token para pruebas
    
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    long expired = nowMillis + 999999;
    
    String token = Jwts.builder().setIssuedAt(now)
    		.setSubject("usertest")
    		.claim("Rol", "admin")
			.setExpiration(new Date(expired))
			.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
    
    @Test
    public void testInterestedUsers() throws IOException, JSONException {
        int placeId = 1;

        HttpUriRequest request = new HttpGet(getApiUrl() + "place/" + placeId + "/interested");
        
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);

        int interestedUsers = json.getInt("interestedUsers");

        Assert.assertTrue(interestedUsers >= 0);
    }

    @Test
    public void testTotalRegisteredPlaces() throws  IOException, JSONException {
        HttpUriRequest request = new HttpGet(getApiUrl() + "/place/dashboard");
        
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);

        int commonPlaces = json.getInt("addedPlaces");

        Assert.assertTrue(commonPlaces >= 0);
    }

}
