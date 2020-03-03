package models;

import java.util.Objects;

/**
 * Database model of the Event Database Table
 */
public class Event {
    /**
     * username associated with event
     */
    private String associatedUsername;
    /**
     * PK ID of event
     */
    private String eventID;
    /**
     * ID of person associated with event
     */
    private String personID;
    /**
     * Latitude of event location
     */
    private Float latitude;
    /**
     * Longitude of event location
     */
    private Float longitude;
    /**
     * Country of event
     */
    private String country;
    /**
     * City of event location
     */
    private String city;
    /**
     * Type of event
     */
    private String eventType;
    /**
     * Year event occurred
     */
    private Integer year;

    /**
     * Constructor
     *
     * @param associatedUsername Username the event is tied to
     * @param eventID            Unique event identifier; string
     * @param personID           A ID that is unique to the person the event is tied to
     * @param latitude           direction on earth
     * @param longitude          direction on earth
     * @param country            Nation in which event occured
     * @param city               City in which event occurred
     * @param eventType          String: type of event
     * @param year               Year of which event occurred
     */
    public Event(String associatedUsername, String eventID, String personID, Float latitude, Float longitude, String country, String city, String eventType, Integer year) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return associatedUsername.equals(event.associatedUsername) &&
                eventID.equals(event.eventID) &&
                personID.equals(event.personID) &&
                latitude.equals(event.latitude) &&
                longitude.equals(event.longitude) &&
                country.equals(event.country) &&
                city.equals(event.city) &&
                eventType.equals(event.eventType) &&
                year.equals(event.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedUsername, eventID, personID, latitude, longitude, country, city, eventType, year);
    }
}
