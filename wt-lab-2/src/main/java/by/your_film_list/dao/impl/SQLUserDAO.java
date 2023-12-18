package by.your_film_list.dao.impl;

import by.your_film_list.bean.User;
import by.your_film_list.bean.UserPrivilegeRole;
import by.your_film_list.dao.UserDAO;
import by.your_film_list.dao.exception.DAOException;
import com.google.common.hash.Hashing;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * The SQLUserDAO class is an implementation of the UserDAO interface that interacts with a SQL database.
 * It provides methods for accessing and manipulating user data in the database.
 */
public class SQLUserDAO implements UserDAO {
    /**
     * The logger used for logging messages and exceptions in the SQLUserDAO class.
     */
    private static final Logger logger = LogManager.getLogger(SQLUserDAO.class);

    /**
     * Extracts a User object from the ResultSet.
     *
     * @param rs the ResultSet containing user data
     * @return the extracted User object
     * @throws SQLException if an error occurs while extracting the user data
     */
    private User extractUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("login"),
                UserPrivilegeRole.valueOf(
                        rs.getString("role_name")
                        .toUpperCase()),
                rs.getFloat("status_value"),
                rs.getBoolean("ban")
        );
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user with the specified ID
     * @throws DAOException if an error occurs while retrieving the user
     */
    @Override
    public User getUser(int userId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String sql = "SELECT u.*, r.name AS role_name FROM user u join role r" +
                " ON u.role_id = r.id WHERE u.id = " + userId + ";";
        User user = null;
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in getting user from db.");
            throw new DAOException(e.getMessage());
        }
        return user;
    }

    /**
     * Authenticates a user by their login and password.
     *
     * @param login    the user's login
     * @param password the user's password
     * @return the authenticated user
     * @throws DAOException if an error occurs while authenticating the user
     */
    @Override
    public User signIn(String login, String password) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String passwordHash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        String sql = String.format(
                "select user.*, r.name as role_name from user "
                        + "join role r on user.role_id = r.id "
                        + "where user.login = '%s' and user.password = '%s' "
                        + "limit 1;",
                login, passwordHash);

        User user = null;
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in user sign in db level.");
            throw new DAOException(e.getMessage());
        }
        return user;
    }

    /**
     * Registers a new user with the specified login and password.
     *
     * @param login    the user's login
     * @param password the user's password
     * @return the registered user
     * @throws DAOException if an error occurs while registering the user
     */
    @Override
    public User register(String login, String password) throws DAOException {
        int roleId = 3;
        float statusValue = 0.0f;
        String imagePath = null;
        boolean isBanned = false;
        String passwordHash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String registerUserSql = "INSERT INTO user"
                + "(login, password, role_id, status_value, image_path, ban)"
                + "values(?, ?, ?, ?, ?, ?);";

        try (Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.
                    prepareStatement(registerUserSql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setInt(3, roleId);
            preparedStatement.setDouble(4, statusValue);
            preparedStatement.setString(5, imagePath);
            preparedStatement.setBoolean(6, isBanned);

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        int userId = rs.getInt(1);
                        return new User(userId, login, UserPrivilegeRole.USER, statusValue, isBanned);
                    }
                }
            }
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in user registration db level");
            throw new DAOException(e.getMessage());
        }
        return null;
    }

    /**
     * Bans a user with the specified user ID.
     *
     * @param userId the ID of the user to ban
     * @throws DAOException if an error occurs while banning the user
     */
    @Override
    public void ban(int userId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

        String sql = "UPDATE user SET ban = TRUE WHERE id = ?;";
        int nEffectedRows;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            nEffectedRows = preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in user sign in db level.");
            throw new DAOException(e.getMessage());
        }
        if (nEffectedRows != 1) {
            throw new DAOException("Number of effected rows after updating ban status != 1");
        }
    }

    /**
     * Unbans a user with the specified user ID.
     *
     * @param userId the ID of the user to unban
     * @throws DAOException if an error occurs while unbanning the user
     */
    @Override
    public void unban(int userId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

        String sql = "UPDATE user SET ban = FALSE WHERE id = ?;";
        int nEffectedRows;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            nEffectedRows = preparedStatement.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in user unban db level.");
            throw new DAOException(e.getMessage());
        }
        if (nEffectedRows != 1) {
            throw new DAOException("Number of effected rows after updating ban status != 1");
        }
    }
}
