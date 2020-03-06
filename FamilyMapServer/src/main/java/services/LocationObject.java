package services;

public class LocationObject {
/**
 * "country": "Canada",
 *       "city": "Grise Fiord",
 *       "latitude": 76.4167,
 *       "longitude": -81.1
 */
    private String country;
    private String city;
    private Float latitude;
    private Float longitude;

    public LocationObject(String country, String city, Float latitude, Float longitude) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
