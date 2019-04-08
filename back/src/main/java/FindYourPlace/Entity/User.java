package FindYourPlace.Entity;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class User {

    private final long id;
    private String username;
    private String password;
    private String role;
    private List<PlaceList> placeLists;

    private final AtomicLong counter = new AtomicLong();

    public User(String username, String password) {
        this.id = counter.incrementAndGet();
        this.username = username;
        this.password = password;
        this.role = "USER";
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
