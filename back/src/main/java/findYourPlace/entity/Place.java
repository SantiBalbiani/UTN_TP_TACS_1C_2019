package findYourPlace.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import findYourPlace.utils.PlaceDeserializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@JsonDeserialize(using = PlaceDeserializer.class)

//@CompoundIndex(def = "{'fortsquareId':1, 'userId':1, 'listName':1}", name = "compound_index_1", unique=true)
@Document
public class Place {

    @Id
    private String id;
    private String fortsquareId;
    private String userId;
    private String listName;
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
    private Date addedAt;

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
        this.fortsquareId = placeId;
        this.name = name;
    }

    @PersistenceConstructor
    public Place(String fortsquareId, String name, String address, float latitude, float longitude, String postalCode, String cc, String city, String state, String country) {
        this.fortsquareId = fortsquareId;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postalCode = postalCode;
        this.cc = cc;
        this.city = city;
        this.state = state;
        this.country = country;
        this.addedAt = new Date();
    }

    public Place(){}

    public String getFortsquareId() {
        return fortsquareId;
    }

    public void setFortsquareId(String placeId) {
        this.fortsquareId = placeId;
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

    public Date getAddedAt() {return addedAt;}

    public boolean getVisited() {return visited;}

    public String getUserId() { return userId; }

    public void setUserId(String userId) {this.userId = userId; }

    public String getListName() { return listName; }

    public void setListName(String listName) { this.listName = listName; }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    @Override
    public String toString() {
        return "**" +fortsquareId + "** - " + name + (address != null ? " (" + address + ") " : "");
    }
}
