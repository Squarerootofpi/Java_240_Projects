package daos;
import java.sql.Connection;
import java.sql.SQLException;

import models.User;

/**
 * {@inheritDoc}
 * Accessors for User Table in DB
 */
public class UserDao implements IDao  {
    /**
     * Connection for the Dao to access
     */
    private Connection conn;

    /**
     * UserDau constructor
     * @param conn the passed in connection from the service.
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * User update function for the database: updates with a given model.
     * @param user the model to identify the row for the Dao to attempt to update
     * @return says whether it updated properly
     */
    public boolean update(User user)
    {
        return false;
    }

    /**
     * User table reading function
     * @param id The ID (PK) of the model to find
     * @return the model associated with the ID
     */
    public User read(String id) {

        return null;
    }

    /**
     * User add-row function, accepts a model to do so.
     * @param user the model for the Dao to attempt to create a row from
     * @return indicating if the row was successfully created.
     */
    public boolean create(User user)
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
