package by.your_film_list.dao.impl;

import by.your_film_list.dao.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Class that represents pool of connection to mysql database.
 */
public class ConnectionPool {
    /**
     * Constant, that represents capacity of connection pool.
     */
    private static final int POOL_CAPACITY = 15;
    /**
     * Constant, that represents address of jdbc.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/my_anime_list";
    /**
     * Constant, that represents login for mysql.
     */
    private static final String USER = "root";
    /**
     * Constant, that represents password for mysql.
     */
    private static final String PASSWORD = "somepassword";
    /**
     * Constant, that represents TIME_OUT of getting connection from pool.
     */
    private static final long TIME_OUT_SECONDS = 2;
    /**
     * Connection pool object for singleton pattern.
     */
    private static ConnectionPool connectionPool = null;
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    /**
     * Blocking queue of connection to database.
     */
    private final BlockingQueue<Connection> connections;

    /**
     * Returns the instance of ConnectionPool.
     * If the instance is not created, it creates a new instance and returns it.
     * @return The ConnectionPool instance.
     * @throws DAOException if there is an error in creating the connection pool.
     */
    public static ConnectionPool getConnectionPool() throws DAOException {
        if (connectionPool == null) {
            try {
                connectionPool = new ConnectionPool(URL, USER, PASSWORD, POOL_CAPACITY);
            } catch (SQLException e) {
                logger.log(Level.FATAL, "Error in creating connection pool.");
                throw new DAOException(e.getMessage());
            }
        }

        return connectionPool;
    }

    /**
     * Retrieves a connection from the pool.
     * If no connection is available, it returns null.
     * @return The retrieved Connection object.
     * @throws InterruptedException if the thread is interrupted while waiting for a connection.
     */
    public Connection getConnection() throws InterruptedException {
        Connection connection = connections.poll(TIME_OUT_SECONDS, TimeUnit.SECONDS);
        if (connection == null) {
            logger.log(Level.WARN, "No connection for db are free.");
            return null;
        }

        logger.log(Level.TRACE, "Getting db connection.");
        return ConnectionProxy.createProxy(connection, connectionPool);
    }

    /**
     * Returns a connection to the pool.
     * @param connection The Connection object to be returned.
     */
    public void returnConnection(Connection connection) {
        logger.log(Level.TRACE, "Returning db connection.");
        connections.add(connection);
    }

    /**
     * Constructs a ConnectionPool object with the specified URL, user, password, and capacity.
     *
     * @param url      the URL of the database
     * @param user     the username for the database connection
     * @param password the password for the database connection
     * @param capacity the maximum number of connections in the pool
     * @throws SQLException if a database access error occurs
     */
    public ConnectionPool(String url, String user, String password, int capacity) throws SQLException {
        connections = new ArrayBlockingQueue<>(capacity);
        for (int i = 0; i < capacity; i++) {
            connections.add(DriverManager.getConnection(url, user, password));
        }
    }
}
