package requests;
import models.*;

/**
 * Load Request object: contains the array of users, people, and
 * events that user requests to be put in the database.
 */
public class Load {
    /**
     * The array of Users to add
     */
    private User[] users;
    /**
     * The array of person objects to add
     */
    private Person[] persons;
    /**
     * The array of events to add
     */
    private Event[] events;

    /**
     * Constructor for the internal load request object
     * @param users The array or users
     * @param persons The array of people
     * @param events The array of events
     */
    public Load(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
