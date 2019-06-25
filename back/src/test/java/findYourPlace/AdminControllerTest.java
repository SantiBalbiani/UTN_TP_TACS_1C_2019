package findYourPlace;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

import findYourPlace.security.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.io.IOException;
import java.util.Date;

/**
 * Created by icernigoj on 11/05/2019.
 */

@SpringBootTest

public class AdminControllerTest {

    private String serverPort="8080";
    private String serverAddress = "http://springboot";
    private String getApiUrl() {
        return serverAddress + ":" + serverPort + "/admin";
    }

    //Se crea token para pruebas
    
    static long nowMillis = System.currentTimeMillis();
    static Date now = new Date(nowMillis);
    static long expired = nowMillis + 999999;
    
    static final String initialUsername = "pepe" + RandomStringUtils.random(12, true, false).toLowerCase();
    
    static final String  token = Jwts.builder().setIssuedAt(now)
    		.setSubject(initialUsername)
    		.claim("Rol", "admin")
			.setExpiration(new Date(expired))
			.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
    
    String InitialUserId;
    
    @Test
    @Order(1)  
    public void testCreateInitialUser() throws IOException, JSONException {

        //Create user, get user, create list, get list, delete list
    	InitialUserId = testCreateUser(initialUsername, "admin");

    }
    
    @Test
    @Order(2)
    public void testInterestedUsers() throws IOException, JSONException {
        int placeId = 1;

        String url = getApiUrl() +"/"+ "place/" + placeId + "/interested";
        
        HttpUriRequest request = new HttpGet(url);
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);

        int interestedUsers = json.getInt("interestedUsers");

        Assert.assertTrue(interestedUsers >= 0);
    }

    @Test
    @Order(3)
    public void testTotalRegisteredPlaces() throws  IOException, JSONException {
    	String url = getApiUrl() +"/"+ "place/dashboard";
        
        HttpUriRequest request = new HttpGet(url);
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);

        int commonPlaces = json.getInt("addedPlaces");

        Assert.assertTrue(commonPlaces >= 0);
    }
    
    @Test
    @Order(4)
    public void testDeleteinitialUser() throws IOException, JSONException {

    	testDeleteUser(InitialUserId);
    }
    
    public void testDeleteUser(String userId) throws IOException, JSONException {
    	String url = serverAddress + ":" + 8080 +"/"+ "user" +"/"+ userId;
        HttpDelete request = new HttpDelete(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
    }
    
    public String testCreateUser(String username,String role) throws IOException, JSONException {
        String url = serverAddress + ":" + 8080 + "/user";
        HttpPost request = new HttpPost(url);

        request.setHeader("Content-Type", "application/json");

        request.setEntity(new StringEntity("{\"username\":\"" + username + "\"," +
                "\"role\":\"" + role + "\"," +
                "\"password\":\"passdsfd\""+
                "}",
                ContentType.create("application/json")
                ));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String userId = json.getString("id");
        Assert.assertNotEquals(null,userId);
        return userId;
    }

}
