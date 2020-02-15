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
     * @param associatedUsername Username the event is tied to
     * @param eventID Unique event identifier; string
     * @param personID A ID that is unique to the person the event is tied to
     * @param latitude direction on earth
     * @param longitude direction on earth
     * @param country Nation in which event occured
     * @param city City in which event occurred
     * @param eventType String: type of event
     * @param year Year of which event occurred
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
}
