package findYourPlace;

import findYourPlace.security.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.util.Date;

/**
 * Created by icernigoj on 11/05/2019.
 */


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    private String serverPort="8080";
    private String serverAddress = "http://localhost";
    private String getApiUrl() {
        return serverAddress + ":" + serverPort + "/user";
    }
    
    //Se crea token para pruebas
    
    static final long nowMillis = System.currentTimeMillis();
    static final Date now = new Date(nowMillis);
    static final long expired = nowMillis + 999999;
    
    static final String initialUsername = "pepe" + RandomStringUtils.random(12, true, false).toLowerCase();
    
    static final Date expirationDate = new Date(expired);
    
    static final String  token = Jwts.builder().setIssuedAt(now)
    		.setSubject(initialUsername)
    		.claim("Rol", "user")
			.setExpiration(expirationDate)
			.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
    
    static String InitialUserId;
    
    @Test
    public void testACreateInitialUser() throws IOException, JSONException {

    	UserControllerTest.InitialUserId = testCreateUser(initialUsername,"user");

    }
    
    @Test
    public void testBUserHappyPath() throws IOException, JSONException {


        String nuevoUsername = "pepe" + RandomStringUtils.random(12, true, false).toLowerCase();
        String listName = "placeList";
        String newListName = "newPlaceList";
        String placeId = "4dbfe4431e72dd48b1fb606c";

        //Create user, get user, create list, get list, delete list
        String id = testCreateUser(nuevoUsername,"user");
        String tokenUser = getTokenFromUsername(nuevoUsername);
        testGetUser(nuevoUsername,id,tokenUser);
        testCreatePlaceList(listName,tokenUser);
        testGetPlaceLists(tokenUser);
        testGetPlaceListByName(listName,tokenUser);
        testModifyPlaceList(listName,newListName,tokenUser);
        testAddPlaceToUserPlace(newListName,placeId,tokenUser);
        testGetPlaceFromUserPlaces(newListName,placeId,tokenUser);
        testMarkPlaceAsVisited(newListName,placeId,tokenUser);
        testDeletePlaceFromUserPlaces(newListName,placeId,tokenUser);
        testDeletePlaceList(newListName,tokenUser);
        
        testDeleteUser(tokenUser);

    }

    private String getTokenFromUsername(String initialUsername) {
        return Jwts.builder().setIssuedAt(now).setSubject(initialUsername).claim("Rol", "user").setExpiration(new Date(expired)).signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
    }

    @Test
    public void testDAddPlaceToNonExistingUserPlace() throws IOException, JSONException {


        testAddPlaceToNonExistingUserPlace(InitialUserId,"nonExistingListName","placeId");

    }


    @Test
    public void testFMarkNonExistingPlaceAsVisited() throws IOException, JSONException {

        String listName = "placeList";

        testCreatePlaceList(listName,token);
        testMarkNonExistingPlaceAsVisited(listName,"nonExistingPlaceId");

    }

    @Test
    public void testGModifyNonExistingPlaceListInExistingUser() throws IOException, JSONException {

        String listName = "placeListInventada";
        String newUser = "nuevoUser";

        testCreateUser(newUser,"user");
        String newToken = getTokenFromUsername(newUser);
        testModifyNonExistingPlaceList(newToken, listName);

        //Delete created user
        testDeleteUser(newToken);
    }

    @Test
    public void testHGetNotExistingUser() throws IOException, JSONException {
        String url = getApiUrl();//+"/"+"notExistingUserId";
        HttpUriRequest request = new HttpGet(url);
        request.setHeader(HttpHeaders.AUTHORIZATION, token+"sdsd");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR,httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testICreatePlaceListInNonExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"notExistingUserId"+"/"+"place_list";
        HttpPost request = new HttpPost(url);

        request.setHeader("Content-Type", "application/json");
        request.setHeader(HttpHeaders.AUTHORIZATION, token+"ef");
        
        request.setEntity(new StringEntity("{\"name\":\"" + "list_name"+ "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR,httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testJCreateDuplicatedUser() throws IOException, JSONException {

        testCreateExistingUser(initialUsername,"user");

    }

    
    @Test
    public void testKDeleteinitialUser() throws IOException, JSONException {

    	testDeleteUser(token);
    }
    

    @Test
    public void testMModifyPlaceListFromNotExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+"placeListCurrentName"+"/"+"placeListName";
        HttpPatch request = new HttpPatch(url);
        request.setHeader("Content-Type", "application/json");
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 
        request.setEntity(new StringEntity("placeListName",
                ContentType.create("application/json")));


        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_FORBIDDEN,httpResponse.getStatusLine().getStatusCode());
    }    
    
    @Test
    public void testNGetPlaceListsFromNotExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list" + "/" + "listaX";
        HttpUriRequest request = new HttpGet(url);
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_FORBIDDEN,httpResponse.getStatusLine().getStatusCode());
    }
    
    public void testMarkNonExistingPlaceAsVisited(String placeListName, String placeId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+placeListName+"/"+"place"+"/"+placeId;
        HttpPatch request = new HttpPatch(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    public void testAddPlaceToUserPlace(String placeListName, String placeId,String token) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+placeListName;
        HttpPut request = new HttpPut(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 
        request.setEntity(new StringEntity("{\"id\":\"" + placeId +"\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String retrievedListName = json.getString("name");
        Assert.assertEquals(placeListName,retrievedListName);
    }

    public void testAddPlaceToNonExistingUserPlace(String username, String placeListName, String placeId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+placeListName;
        HttpPut request = new HttpPut(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 
        request.setEntity(new StringEntity("{\"placeId\":\"" + placeId +"\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());

    }

    public void testDeletePlaceFromUserPlaces(String placeListName, String placeId,String token) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+placeListName+"/"+"place"+"/"+placeId;
        HttpDelete request = new HttpDelete(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String retrievedListName = json.getString("name");
        Assert.assertEquals(placeListName,retrievedListName);
    }

    public void testGetPlaceFromUserPlaces(String placeListName, String placeId, String token) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+placeListName+"/"+"place"+"/"+placeId;
        HttpGet request = new HttpGet(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String retrievedPlaceId = json.getString("fortsquareId");
        Assert.assertEquals(placeId,retrievedPlaceId);
    }

    public void testMarkPlaceAsVisited(String placeListName, String placeId, String token) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+placeListName+"/"+"place"+"/"+placeId;
        HttpPatch request = new HttpPatch(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        Assert.assertTrue(json.getBoolean("visited"));
    }

    public String testCreateUser(String username,String role) throws IOException, JSONException {
        String url = getApiUrl();
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

    public void testCreateExistingUser(String username,String role) throws IOException, JSONException {
        String url = getApiUrl();
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 
        request.setEntity(new StringEntity("{\"username\":\"" + username + "\"," +
                "\"role\":\"" + role + "\"," +
                "\"password\":\"passdsfd\""+
                "}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_CONFLICT,httpResponse.getStatusLine().getStatusCode());
    }

    public void testCreatePlaceList(String listName, String token) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list";
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 
        request.setEntity(new StringEntity("{\"listName\":\"" + listName+ "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
    }

    public void testGetPlaceListByName(String listName,String token) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+listName;
        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String retrievedListName = json.getString("name");
        Assert.assertEquals(listName,retrievedListName);
    }

    public void testGetPlaceLists(String token) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list";
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONArray jsonArray = new JSONArray(resultString);
        Assert.assertEquals(1,jsonArray.length());
    }

    public void testGetUser(String initialUsername, String userId,String token) throws IOException, JSONException {
        String url = getApiUrl();
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String username = json.getString("username");
        Assert.assertEquals(initialUsername,username);
    }

    public void testModifyNonExistingPlaceList(String token, String name) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+"nonExistingPlaceList"+"/"+name;
        HttpPatch request = new HttpPatch(url);
        request.setHeader("Content-Type", "application/json");

        request.addHeader(HttpHeaders.AUTHORIZATION, token);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    public void testModifyPlaceList(String placeListCurrentName, String placeListName,String token) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+placeListCurrentName+"/"+placeListName;
        HttpPatch request = new HttpPatch(url);
        request.setHeader("Content-Type", "application/json");

        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        request.setEntity(new StringEntity(placeListName,
                ContentType.create("application/json")));


        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String generatedListName = json.getString("name");
        Assert.assertEquals(placeListName,generatedListName);
    }

    public void testDeletePlaceList(String placeListName,String token) throws IOException, JSONException {
        String url = getApiUrl()+"/"+"place_list"+"/"+placeListName;
        HttpDelete request = new HttpDelete(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        JSONArray jsonArray = json.getJSONArray("placeLists");
        Assert.assertEquals(0,jsonArray.length());
    }

    public void testDeleteUser(String token) throws IOException, JSONException {
        String url = getApiUrl();
        HttpDelete request = new HttpDelete(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
    }
}
