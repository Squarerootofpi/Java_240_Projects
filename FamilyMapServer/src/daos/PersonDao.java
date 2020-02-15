package daos;
import models.Person;

import java.sql.Connection;

/**
 * {@inheritDoc}
 * Accessors for Person Table in DB
 */
public class PersonDao implements IDao {
    /**
     * Connection for the Dao to access
     */
    private Connection conn;

    /**
     * PersonDao constructor
     * @param conn the passed in connection from the service.
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Person update function for the database: updates with a given model.
     * @param person the model to identify the row for the Dao to attempt to update
     * @return says whether it updated properly
     */
    public boolean update(Person person)
    {
        return false;
    }

    /**
     * Person table reading function
     * @param id The ID (PK) of the model to find
     * @return the model associated with the ID
     */
    public Person read(String id) {

        return null;
    }

    /**
     * Person add-row function, accepts a model to do so.
     * @param person the model for the Dao to attempt to create a row from
     * @return indicating if the row was successfully created.
     */
    public boolean create(Person person)
    {

        return false;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(String id) {
        return false;
    }
}
