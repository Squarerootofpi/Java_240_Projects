package daos;


import models.AuthToken;

import java.sql.Connection;

/**
 * {@inheritDoc}
 * Accessors for AuthToken Table in DB
 */
public class AuthTokenDao implements IDao{
    /**
     * Connection for the Dao to access
     */
    private Connection conn;

    /**
     * Constructor for authDau, stores the connection as a private
     * variable, and references that same connection in its lifetime
     * @param conn the DB connection given to it by the service.
     */
    public AuthTokenDao(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * AuthToken update function for the database: updates with a given model.
     * @param authToken the model to identify the row for the Dao to attempt to update
     * @return says whether it updated properly
     */
    public boolean update(AuthToken authToken)
    {
        return false;
    }

    /**
     * AuthToken table reading function
     * @param id The ID (PK) of the model to find
     * @return the model associated with the ID
     */
    public AuthToken read(String id) {

        return null;
    }

    /**
     * Authtoken add-row function, accepts a model to do so.
     * @param authToken the model for the Dao to attempt to create a row from
     * @return indicating if the row was successfully created.
     */
    public boolean create(AuthToken authToken)
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
     *
     * {@inheritDoc}
     */
    @Override
    public boolean delete(String id) {
        return false;
    }
}
