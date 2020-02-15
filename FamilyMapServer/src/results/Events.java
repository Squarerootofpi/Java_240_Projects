package results;

import models.Event;

/**
 * /event response object: contains a list of all events for ALL
 * family members of the current user.
 */
public class Events extends Response {
    private Event[] data;

    /**
     * Constructor that accepts an array of events to constructs the
     * response object for /event
     * @param events The array of event objects
     */
    public Events(Event[] events) {
        this.data = events;
        setSuccess(true);
    }

    public Event[] getEvents() {
        return data;
    }

    public void setEvents(Event[] events) {
        this.data = events;
    }
}
