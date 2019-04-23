package findYourPlace.connector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FourSquareConnector {
	
    //private static final DateFormat dateFormat = new SimpleDateFormat("YYYYMMDD");
    
    //private static final Calendar cal = Calendar.getInstance();

    //private static final String version = dateFormat.format(cal);

    private static final String clientid  = "EDEGFNK514HEKEOU3EGVJWCJZU15TVBQUT34KWAWGVAFLCJW";

    private static final String clientsecret = "HKFKHYZQ2EE3MF4Z1I4SINY0GYMRUKCBGJCNSX1QH0HOVRKM";
	
    private static final String host = "https://api.foursquare.com/v2/venues/search?ll=40.7,-74&client_id=" + clientid + "&client_secret=" + clientsecret + "&v=" + "20192204";
    
    public static String getHost()
    {
    	return host;
    }

    public FourSquareConnector() {
    }
    
/*
    public String getCoin(String coin) {
        String url = HOST + "/v1/ticker/" + coin;

        try {
            return this.getPageContent(url).get(0);
        } catch (ServerResponseException e) {
            throw new CoinNotFoundException(coin, COIN_MARKET);
        }

    }
    */
    ///new SimpleDateFormat("YYYYMMDD");

}
