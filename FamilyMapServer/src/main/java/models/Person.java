package models;

import java.util.Objects;

/**
 * Database model of the Person Database Table
 */
public class Person {
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
     * @param personID Person’s unique ID
     * @param firstName Person’s first name
     * @param lastName Person’s last name
     * @param gender Person’s gender (m or f)
     */
    public Person(String associatedUsername, String personID, String firstName, String lastName, Character gender) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return associatedUsername.equals(person.associatedUsername) &&
                personID.equals(person.personID) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                gender.equals(person.gender) &&
                Objects.equals(fatherID, person.fatherID) &&
                Objects.equals(motherID, person.motherID) &&
                Objects.equals(spouseID, person.spouseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedUsername, personID, firstName, lastName, gender, fatherID, motherID, spouseID);
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
