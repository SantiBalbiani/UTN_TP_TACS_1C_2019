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

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.IOException;

/**
 * Created by icernigoj on 11/05/2019.
 */

public class AdminControllerTest {

    @Value("${server.port}")
    private String serverPort;

    private String serverAddress = "http://localhost";

    private String getApiUrl() {
        return serverAddress + ":" + 8080 + "/";
    }

    @Test
    public void testListComparator() throws IOException, JSONException {
        int listId1 = 1;
        int listId2 = 2;

        HttpUriRequest request = new HttpGet(getApiUrl() + "list_comparator/" + listId1 + "/" + listId2);

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

        String resultString = EntityUtils.toString(httpResponse.getEntity());

        System.out.print("result");
        System.out.print(resultString);

        JSONObject json = new JSONObject(resultString);

        JSONArray commonPlaces = json.getJSONArray("commonPlaces");

        Assert.assertThat(commonPlaces, instanceOf(JSONArray.class));
    }

    @Test
    public void testInterestedUsers() throws IOException, JSONException {
        int placeId = 1;

        HttpUriRequest request = new HttpGet(getApiUrl() + "place/" + placeId + "/interested");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);

        int interestedUsers = json.getInt("interestedUsers");

        Assert.assertTrue(interestedUsers >= 0);
    }

    @Test
    public void testTotalRegisteredPlaces() throws  IOException, JSONException {
        HttpUriRequest request = new HttpGet(getApiUrl() + "/dashboard/place");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject json = new JSONObject(resultString);

        int commonPlaces = json.getInt("totalRegisteredPlaces");

        Assert.assertTrue(commonPlaces >= 0);
    }

}
