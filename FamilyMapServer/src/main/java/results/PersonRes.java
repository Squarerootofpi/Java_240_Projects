package results;

import models.Person;

/**
 * The /person/[personID] response upon success: contains all the info
 * for a person and a bool indicating successful.
 */
public class PersonRes extends Response {
    /**
     * Username of User who owns this person in DB
     */
    private String associatedUsername;
    /**
     * ID PK of person in dB
     */
    private String personID;
    /**
     * Firstname of person
     */
    private String firstName;
    /**
     * Last name of person
     */
    private String lastName;
    /**
     * Gender of person
     */
    private Character gender;
    /**
     * Optional: Father of person's ID
     */
    private String fatherID;
    /**
     * Optional: Mother of person's ID
     */
    private String motherID;
    /**
     * Optional: ID of the person's spouse
     */
    private String spouseID;

    /**
     * Constructor of person object
     *
     * @param associatedUsername Name of the user account this person belongs to
     * @param personID           Person’s unique ID
     * @param firstName          Person’s first name
     * @param lastName           Person’s last name
     * @param gender             Person’s gender (m or f)
     */
    public PersonRes(String associatedUsername, String personID, String firstName, String lastName, Character gender) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
        setSuccess(true);
    }

    /**
     * Constructs a person response based on a passed in person model object
     *
     * @param person A passed in model of a person object,
     *               since they are almost identical
     */
    public PersonRes(Person person) {
        this.associatedUsername = person.getAssociatedUsername();
        this.personID = person.getPersonID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getFatherID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getSpouseID();
        setSuccess(true);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
