package daos;
import models.Event;

import java.sql.Connection;

/**
 * {@inheritDoc}
 * Accessors for Event Table in DB
 */
public class EventDao implements IDao {
    /**
     * Connection for the Dao to access
     */
    private Connection conn;

    /**
     * EventDao constructor
     * @param conn the passed in connection from the service.
     */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Event update function for the database: updates with a given model.
     * @param event the model to identify the row for the Dao to attempt to update
     * @return says whether it updated properly
     */
    public boolean update(Event event)
    {
        return false;
    }

    /**
     * Event table reading function
     * @param id The ID (PK) of the model to find
     * @return the model associated with the ID
     */
    public Event read(String id) {

        return null;
    }

    /**
     * Event add-row function, accepts a model to do so.
     * @param event the model for the Dao to attempt to create a row from
     * @return indicating if the row was successfully created.
     */
    public boolean create(Event event)
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
