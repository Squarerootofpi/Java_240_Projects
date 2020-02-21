package results;

import models.Event;

/**
 * /event response object: contains a list of all events for ALL
 * family members of the current user.
 */
public class AllEvents extends Response {
    /**
     * An array of all the events of a person's tree
     */
    private Event[] data;

    /**
     * Constructor that accepts an array of events to constructs the
     * response object for /event
     *
     * @param events The array of event objects
     */
    public AllEvents(Event[] events) {
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
