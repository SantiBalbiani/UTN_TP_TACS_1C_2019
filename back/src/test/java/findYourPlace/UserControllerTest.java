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
    public void testGetUser() throws IOException, JSONException {
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

    public String testCreateUser(String username,String role) throws IOException, JSONException {
        String url = getApiUrl();
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");

        request.setEntity(new StringEntity("{\"username\":\"" + username + "\",\"role\":\"" + role + "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String userId = json.getString("id");
        Assert.assertNotEquals(userId,null);
        return userId;
    }

    public void testCreatePlaceList(String userId,String listName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list";
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");

        request.setEntity(new StringEntity("{\"name\":\"" + listName+ "\"}",
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        JSONArray jsonArray = json.getJSONArray("placeLists");
        JSONObject list = jsonArray.getJSONObject(jsonArray.length()-1);
        String generatedListName = list.getString("name");
        Assert.assertEquals(generatedListName,listName);
    }

    public void testGetPlaceLists(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list";
        HttpUriRequest request = new HttpGet(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONArray jsonArray = new JSONArray(resultString);
        Assert.assertEquals(1,jsonArray.length());
    }

    public void testGetUser(String initialUsername, String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId;
        HttpUriRequest request = new HttpGet(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String username = json.getString("username");
        Assert.assertEquals(username,initialUsername);
    }

    public void testModifyPlaceList(String userId,String placeListCurrentName, String placeListName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListCurrentName;
        HttpPatch request = new HttpPatch(url);
        request.setHeader("Content-Type", "application/json");

        request.setEntity(new StringEntity(placeListName,
                ContentType.create("application/json")));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        JSONArray jsonArray = json.getJSONArray("placeLists");
        JSONObject list = jsonArray.getJSONObject(jsonArray.length()-1);
        String generatedListName = list.getString("name");
        Assert.assertEquals(generatedListName,placeListName);
    }

    public void testDeletePlaceList(String userId,String placeListName) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId+"/"+"place_list"+"/"+placeListName;
        HttpDelete request = new HttpDelete(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        JSONArray jsonArray = json.getJSONArray("placeLists");
        Assert.assertEquals(jsonArray.length(),0);
    }

    public void testDeleteUser(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId;
        HttpDelete request = new HttpDelete(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }
}
