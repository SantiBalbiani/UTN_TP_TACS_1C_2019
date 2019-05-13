package findYourPlace;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.ExceptionUtils;
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
        double x = Math.random();
        String initialUsername = "xbkjgfsa"+String.valueOf(x);
        String id = testCreateUser(initialUsername,"user");
        Assert.assertNotEquals(id,null);
        String username = testGetUser(id);
        Assert.assertEquals(username,initialUsername);
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
        return userId;
    }


    public String testGetUser(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/"+userId;
        HttpUriRequest request = new HttpGet(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);
        String username = json.getString("username");
        return username;
    }

    public void testDeleteUser(String userId) throws IOException, JSONException {
        String url = getApiUrl()+"/delete/"+userId;
        HttpDelete request = new HttpDelete(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }
}
