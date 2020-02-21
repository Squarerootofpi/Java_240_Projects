package results;

import models.Person;

/**
 * /person response class: contains accessors to all family
 * members of current user
 */
public class AllPeople extends Response {
    /**
     * The array of people that are related to the current user
     */
    private Person[] data;

    /**
     * Constructor for /person response object: contains ALL familiy members
     * of the current user.
     *
     * @param people The passed in array of Person model objects
     */
    public AllPeople(Person[] people) {
        this.data = people;
        setSuccess(true);
    }

    public Person[] getPeople() {
        return data;
    }

    public void setPeople(Person[] people) {
        this.data = people;
    }
}
