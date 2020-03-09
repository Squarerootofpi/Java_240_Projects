package results;

import models.Event;

/**
 * /Event/[eventID] response object: it contains the variable for event
 * and a bool to indicate it was successful.
 */
public class EventRes extends Response {
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
     * Constructor of response object just by adding each method
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
    public EventRes(String associatedUsername, String eventID, String personID, Float latitude, Float longitude, String country, String city, String eventType, Integer year) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        setSuccess(true);
    }

    /**
     * Constructor that builds a Event response using an Event model
     *
     * @param event A passed in event model object, to generate a response object from it.
     */
    public EventRes(Event event) {
        this.associatedUsername = event.getAssociatedUsername();
        this.eventID = event.getEventID();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
        setSuccess(true);
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
}
