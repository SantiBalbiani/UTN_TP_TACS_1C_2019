package findYourPlace;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by icernigoj on 11/05/2019.
 */
public class UserControllerTest {

    private String serverPort="8080";
    private String serverAddress = "http://localhost";
    private String getApiUrl() {
        return serverAddress + ":" + serverPort + "/user";
    }

    @Test
    public void testUserHappyPath() throws IOException, JSONException {
        //Set variables
        double x = Math.random();
        String initialUsername = "xbkjgfsa"+String.valueOf(x);
        String listName = "placeList";
        String newListName = "newPlaceList";

        //Create user, get user, create list, get list, delete list
        String id = testCreateUser(initialUsername,"user");
        testGetUser(initialUsername,id);
        testCreatePlaceList(id,listName);
        testGetPlaceLists(id);
        testModifyPlaceList(id,listName,newListName);
        testDeletePlaceList(id,newListName);

        //Delete created user
        testDeleteUser(id);
    }

    @Test
    public void testCreateDuplicatedPlaceList() throws IOException, JSONException {
        //Set variables
        double x = Math.random();
        String initialUsername = "xbkjgfsa"+String.valueOf(x);
        String listName = "placeList";

        //Create user, get user, create list, get list, delete list
        String id = testCreateUser(initialUsername,"user");
        testGetUser(initialUsername,id);
        testCreatePlaceList(id,listName);
        testCreateDuplicatedPlaceList(id,listName);

        //Delete created user
        testDeleteUser(id);
    }

    @Test
    public void testModifyNonExistingPlaceListInExistingUser() throws IOException, JSONException {
        //Set variables
        double x = Math.random();
        String initialUsername = "xbkjgfsa"+String.valueOf(x);
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
    public void testGetNotExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"notExistingUserId";
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testCreatePlaceListInNonExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"notExistingUserId"+"/"+"place_list";
        HttpPost request = new HttpPost(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.CONNECTION, "Close");

        request.setEntity(new StringEntity("{\"name\":\"" + "list_name"+ "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    @Test
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
    public void testGetPlaceListsFromNotExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"nonExsistingUserId"+"/"+"place_list";
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testModifyPlaceListFromNotExistingUser() throws IOException, JSONException {
        String url = getApiUrl()+"/"+"nonExistingUserId"+"/"+"place_list"+"/"+"placeListCurrentName";
        HttpPatch request = new HttpPatch(url);
        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        request.setEntity(new StringEntity("placeListName",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    public String testCreateUser(String username,String role) throws IOException, JSONException {
        String url = getApiUrl();
        HttpPost request = new HttpPost(url);

        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        request.setEntity(new StringEntity("{\"username\":\"" + username + "\",\"role\":\"" + role + "\"}",
                ContentType.create("application/json")));

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
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        request.setEntity(new StringEntity("{\"username\":\"" + username + "\",\"role\":\"" + role + "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_CONFLICT,httpResponse.getStatusLine().getStatusCode());
    }

    public void testCreatePlaceList(String userId,String listName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list";
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.CONNECTION, "Close");
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
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        request.setEntity(new StringEntity("{\"name\":\"" + listName+ "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_CONFLICT,httpResponse.getStatusLine().getStatusCode());
    }

    public void testGetPlaceLists(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list";
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONArray jsonArray = new JSONArray(resultString);
        Assert.assertEquals(1,jsonArray.length());
    }

    public void testGetUser(String initialUsername, String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId;
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String username = json.getString("username");
        Assert.assertEquals(initialUsername,username);
    }

    public void testModifyNonExistingPlaceList(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+"nonExistingPlaceList";
        HttpPatch request = new HttpPatch(url);
        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        request.setEntity(new StringEntity("newPlaceListName",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_NOT_FOUND,httpResponse.getStatusLine().getStatusCode());
    }

    public void testModifyPlaceList(String userId,String placeListCurrentName, String placeListName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListCurrentName;
        HttpPatch request = new HttpPatch(url);
        request.setHeader("Content-Type", "application/json");
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        request.setEntity(new StringEntity(placeListName,
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        JSONArray jsonArray = json.getJSONArray("placeLists");
        JSONObject list = jsonArray.getJSONObject(jsonArray.length()-1);
        String generatedListName = list.getString("name");
        Assert.assertEquals(placeListName,generatedListName);
    }

    public void testDeletePlaceList(String userId,String placeListName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListName;
        HttpDelete request = new HttpDelete(url);
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        JSONArray jsonArray = json.getJSONArray("placeLists");
        Assert.assertEquals(0,jsonArray.length());
    }

    public void testDeleteUser(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId;
        HttpDelete request = new HttpDelete(url);
        request.addHeader(HttpHeaders.CONNECTION, "Close");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,httpResponse.getStatusLine().getStatusCode());
    }
}
