package findYourPlace.entity;

public class AdminResponse {

    String sinceDaysAgo;
    Integer addedPlaces;

    public String getSinceDaysAgo() {
        return sinceDaysAgo;
    }

    public void setSinceDaysAgo(String sinceDaysAgo) {
        this.sinceDaysAgo = sinceDaysAgo;
    }

    public Integer getAddedPlaces() {
        return addedPlaces;
    }

    public void setAddedPlaces(Integer addedPlaces) {
        this.addedPlaces = addedPlaces;
    }
}
