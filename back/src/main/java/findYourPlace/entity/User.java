package findYourPlace.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class User {


    private final long id;
    private String username;
    @JsonIgnore
    private String password;
    private String role;
    private List<PlaceList> placeLists;

    static private final AtomicLong counter = new AtomicLong();


    public User(String username, String password) {
        this.id = counter.incrementAndGet();
        this.username = username;
        this.password = password;
        this.role = "USER";
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public User (
            @JsonProperty("id") long id,
            @JsonProperty("username") String username,
            @JsonProperty ("role") String role,
            @JsonProperty("placeLists") List<PlaceList> placeLists)    {
        this.id = id;
        this.username = username;
        this.role = role;
        this.placeLists = placeLists;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
