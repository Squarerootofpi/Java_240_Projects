package daos;

/**
 * Dao or Database access object. It is to access the Database for
 * the service classes who need it.
 * Contains CRUD operations for Database access
 */
public interface IDao {
    /**
     * The clearALL method: clears all things within a table, and leaves
     * it empty.
     */
    public void clear() throws DataAccessException;

    /**
     * The delete-row function, accepts an ID to do so.
     *
     * @param id The ID (PK) of the model to delete
     * @return indicating if successful deletion.
     */
    public boolean delete(String id);
}
