package findYourPlace.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import findYourPlace.utils.PlaceDeserializer;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@JsonDeserialize(using = PlaceDeserializer.class)
public class Place {

    private String id;
    private String name;
    private boolean visited;
    private String address;
    private float latitude;
    private float longitude;
    private String postalCode;
    private String cc;
    private String city;
    private String state;
    private String country;
    private Date timeStamp;

    static private final AtomicLong counter = new AtomicLong();

    /**
     * @return the visited
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * @param visited the visited to set
     */
    public Boolean setVisited(boolean visited) {
        this.visited = visited;
        return true;
    }

    public Place(String name) {
        this.name = name;
    }

    public Place(String placeId, String name) {
        this.id = placeId;
        this.name = name;
    }

    public Place(String placeId, String name, String address, float latitude, float longitude, String postalCode, String cc, String city, String state, String country) {
        this.id = placeId;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postalCode = postalCode;
        this.cc = cc;
        this.city = city;
        this.state = state;
        this.country = country;
        this.timeStamp = new Date();
    }

    public Place(){}

    public String getId() {
        return id;
    }

    public void setPlaceId(String placeId) {
        this.id = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getTimeStamp() {return timeStamp;}

    @Override
    public String toString() {
        return id + " - " + name + " (" + address + ") ";
    }
}
