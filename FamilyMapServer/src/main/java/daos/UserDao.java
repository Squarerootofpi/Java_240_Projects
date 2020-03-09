package daos;

import models.User;

import java.sql.*;

/**
 * {@inheritDoc}
 * Accessors for User Table in DB
 */
public class UserDao implements IDao {
    /**
     * Connection for the Dao to access
     */
    private Connection conn;

    /**
     * UserDau constructor
     *
     * @param conn the passed in connection from the service.
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * User update function for the database: updates with a given model.
     *
     * @param user the model to identify the row for the Dao to attempt to update
     * @return says whether it updated properly
     */
    public boolean update(User user) {
        return false;
    }

    /**
     * User table reading function
     *
     * @param id The ID (PK) of the model to find
     * @return the model associated with the ID
     */
    public User read(String id) throws DataAccessException {

        /*
                this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
         */
            User user;
            ResultSet rs = null;
            String sql = "SELECT * FROM users WHERE userName = ?;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    char genderChar = rs.getString("gender").charAt(0);
                    user = new User( rs.getString("firstName"), rs.getString("lastName"),
                            rs.getString("userName"), rs.getString("email"),
                            rs.getString("password"), genderChar, rs.getString("personID"));
                    return user;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataAccessException("Error encountered while finding event");
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }

        /**
         * User add-row function, accepts a model to do so.
         *
         * @param user the model for the Dao to attempt to create a row from
         * @return indicating if the row was successfully created.
         */
        public boolean create (User user) throws DataAccessException {
                String sql = "INSERT INTO users (firstName, lastName, userName, email, password, gender, personID) " +
                        "VALUES(?,?,?,?,?,?,?)";
                /*
                this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
         */
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    //Using the statements built-in set(type) functions we can pick the question mark we want
                    //to fill in and give it a proper value. The first argument corresponds to the first
                    //question mark found in our sql String
                    stmt.setString(1, user.getFirstName());
                    stmt.setString(2, user.getLastName());
                    stmt.setString(3, user.getUserName());
                    stmt.setString(4, user.getEmail());
                    stmt.setString(5, user.getPassword());
                    stmt.setString(6, String.valueOf(user.getGender()));
                    stmt.setString(7,user.getPersonID());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new DataAccessException("Error encountered while inserting into the database");
                }
                return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear () throws DataAccessException {

            try (Statement stmt = conn.createStatement()){
                String sql = "DELETE FROM users";
                System.out.println(sql);
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                throw new DataAccessException("SQL Error encountered while clearing table users");
            }
        }

        /**
         * {@inheritDoc}
         *
         * NOT IMPLEMENTED!! SO NO TESTING DONE.
         */
        @Override
        public boolean delete (String id){
            return false;
        }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
