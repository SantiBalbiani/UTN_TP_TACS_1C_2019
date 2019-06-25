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
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.util.Date;

/**
 * Created by icernigoj on 11/05/2019.
 */


@SpringBootTest

public class UserControllerTest {

    private String serverPort="8080";
    private String serverAddress = "http://springboot";
    private String getApiUrl() {
        return serverAddress + ":" + serverPort + "/user";
    }
    
    //Se crea token para pruebas
    
    static long nowMillis = System.currentTimeMillis();
    static Date now = new Date(nowMillis);
    static long expired = nowMillis + 999999;
    
    static final String initialUsername = "pepe" + RandomStringUtils.random(12, true, false).toLowerCase();
    
    static final String  token = Jwts.builder().setIssuedAt(now)
    		.setSubject(initialUsername)
    		.claim("Rol", "user")
			.setExpiration(new Date(expired))
			.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
    
    String InitialUserId;
    
    @Test
    @Order(1)  
    public void testCreateInitialUser() throws IOException, JSONException {

        //Create user, get user, create list, get list, delete list
    	InitialUserId = testCreateUser(initialUsername,"user");

    }
    
    @Test
    @Order(2)
    public void testUserHappyPath() throws IOException, JSONException {


        String initialUsername = "pepe" + RandomStringUtils.random(12, true, false).toLowerCase();
        String listName = "placeList";
        String newListName = "newPlaceList";
        String placeId = "4dbfe4431e72dd48b1fb606c";

        //Create user, get user, create list, get list, delete list
        String id = testCreateUser(initialUsername,"user");
        testGetUser(initialUsername,id);
        testCreatePlaceList(id,listName);
        testGetPlaceLists(id);
        testGetPlaceListByName(id,listName);
        testModifyPlaceList(id,listName,newListName);
        testAddPlaceToUserPlace(id,newListName,placeId);
        testGetPlaceFromUserPlaces(id,newListName,placeId);
        testMarkPlaceAsVisited(id,newListName,placeId);
        testDeletePlaceFromUserPlaces(id,newListName,placeId);
        testDeletePlaceList(id,newListName);


        //Delete created user
        testDeleteUser(id);
    }

    @Test
    @Order(3)
    public void addPlaceToNonExistingUserPlace() throws IOException, JSONException {

        String listName = "placeList";

        //Create user, get user, create list, get list, delete list
        String id = testCreateUser(initialUsername,"user");
        testAddPlaceToNonExistingUserPlace(id,"nonExistingListName","placeId");

        //Delete created user
        testDeleteUser(id);
    }

    @Test
    @Order(4)
    public void testCreateDuplicatedPlaceList() throws IOException, JSONException {

        String listName = "placeList";

        //Create user, get user, create list, get list, delete list
        String id = testCreateUser(initialUsername,"user");
        testCreatePlaceList(id,listName);
        testCreateDuplicatedPlaceList(id,listName);

        //Delete created user
        testDeleteUser(id);
    }

    @Test
    @Order(5)
    public void testMarkNonExistingPlaceAsVisited() throws IOException, JSONException {

        String listName = "placeList";

        //Create user, get user, create list, get list, delete list
        String id = testCreateUser(initialUsername,"user");
        testCreatePlaceList(id,listName);
        testMarkNonExistingPlaceAsVisited(id,listName,"nonExistingPlaceId");

        //Delete created user
        testDeleteUser(id);
    }

    @Test
    @Order(6)
    public void testModifyNonExistingPlaceListInExistingUser() throws IOException, JSONException {

        String listName = "placeList";

        //Create user, get user, create list, get list, delete list
        String id = testCreateUser(initialUsername,"user");
        testGetUser(initialUsername,id);
        testCreatePlaceList(id,listName);
        testModifyNonExistingPlaceList(id);

        //Delete created user
        testDeleteUser(id);
    }

    @Test
    @Order(7)
    public void testGetNotExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"notExistingUserId";
        HttpUriRequest request = new HttpGet(url);
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    @Order(8)
    public void testCreatePlaceListInNonExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"notExistingUserId"+"/"+"place_list";
        HttpPost request = new HttpPost(url);

        request.setHeader("Content-Type", "application/json");
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 
        
        request.setEntity(new StringEntity("{\"name\":\"" + "list_name"+ "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    @Order(9)
    public void testCreateDuplicatedUser() throws IOException, JSONException {
        //Set variables
        double x = Math.random();
        String initialUsername = "xbkjgfsa"+String.valueOf(x);

        //Create user, get user, create list, get list, delete list
        String id = testCreateUser(initialUsername,"user");
        testCreateExistingUser(initialUsername,"user");

        //Delete created user
        testDeleteUser(id);
    }

    @Test
    @Order(10)
    public void testGetPlaceListsFromNotExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"nonExsistingUserId"+"/"+"place_list";
        HttpUriRequest request = new HttpGet(url);
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    @Order(11)
    public void testModifyPlaceListFromNotExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"nonExistingUserId"+"/"+"place_list"+"/"+"placeListCurrentName"+"/"+"placeListName";
        HttpPatch request = new HttpPatch(url);
        request.setHeader("Content-Type", "application/json");
        request.setHeader(HttpHeaders.AUTHORIZATION, token); 
        request.setEntity(new StringEntity("placeListName",
                ContentType.create("application/json")));


        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }
    
    @Test
    @Order(12)
    public void testDeleteinitialUser() throws IOException, JSONException {

    	testDeleteUser(InitialUserId);
    }
    
    public void testMarkNonExistingPlaceAsVisited(String userId, String placeListName, String placeId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListName+"/"+"place"+"/"+placeId;
        HttpPatch request = new HttpPatch(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    public void testAddPlaceToUserPlace(String username, String placeListName, String placeId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+username+"/"+"place_list"+"/"+placeListName;
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
        String url = getApiUrl()+"/"+username+"/"+"place_list"+"/"+placeListName;
        HttpPut request = new HttpPut(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 
        request.setEntity(new StringEntity("{\"placeId\":\"" + placeId +"\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());

    }

    public void testDeletePlaceFromUserPlaces(String userId, String placeListName, String placeId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListName+"/"+"place"+"/"+placeId;
        HttpDelete request = new HttpDelete(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String retrievedListName = json.getString("name");
        Assert.assertEquals(placeListName,retrievedListName);
    }

    public void testGetPlaceFromUserPlaces(String userId, String placeListName, String placeId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListName+"/"+"place"+"/"+placeId;
        HttpGet request = new HttpGet(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String retrievedPlaceId = json.getString("fortsquareId");
        Assert.assertEquals(placeId,retrievedPlaceId);
    }

    public void testMarkPlaceAsVisited(String userId, String placeListName, String placeId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListName+"/"+"place"+"/"+placeId;
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

    public void testCreatePlaceList(String userId,String listName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list";
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 
        request.setEntity(new StringEntity("{\"name\":\"" + listName+ "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        JSONArray jsonArray = json.getJSONArray("placeLists");
        JSONObject list = jsonArray.getJSONObject(jsonArray.length()-1);
        String generatedListName = list.getString("name");
        Assert.assertEquals(listName,generatedListName);
    }

    public void testCreateDuplicatedPlaceList(String userId,String listName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list";
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.AUTHORIZATION, token); 
        request.setEntity(new StringEntity("{\"name\":\"" + listName+ "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_CONFLICT,httpResponse.getStatusLine().getStatusCode());
    }

    public void testGetPlaceListByName(String userId,String listName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+listName;
        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String retrievedListName = json.getString("name");
        Assert.assertEquals(listName,retrievedListName);
    }

    public void testGetPlaceLists(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list";
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONArray jsonArray = new JSONArray(resultString);
        Assert.assertEquals(1,jsonArray.length());
    }

    public void testGetUser(String initialUsername, String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId;
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String username = json.getString("username");
        Assert.assertEquals(initialUsername,username);
    }

    public void testModifyNonExistingPlaceList(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+"nonExistingPlaceList"+"/"+"newPlaceListName";
        HttpPatch request = new HttpPatch(url);
        request.setHeader("Content-Type", "application/json");

        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        request.setEntity(new StringEntity("newPlaceListName",
                ContentType.create("application/json")));


        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    public void testModifyPlaceList(String userId,String placeListCurrentName, String placeListName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListCurrentName+"/"+placeListName;
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

    public void testDeletePlaceList(String userId,String placeListName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListName;
        HttpDelete request = new HttpDelete(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        JSONArray jsonArray = json.getJSONArray("placeLists");
        Assert.assertEquals(0,jsonArray.length());
    }

    public void testDeleteUser(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId;
        HttpDelete request = new HttpDelete(url);
        request.addHeader(HttpHeaders.AUTHORIZATION, token);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
    }
}
