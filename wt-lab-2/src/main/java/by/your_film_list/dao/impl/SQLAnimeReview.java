package by.your_film_list.dao.impl;

import by.your_film_list.bean.AnimeReview;
import by.your_film_list.dao.ReviewDAO;
import by.your_film_list.dao.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;

/**
 * SQLAnimeReview is an implementation of the ReviewDAO interface that provides methods for adding and retrieving anime reviews from a SQL database.
 */

public class SQLAnimeReview implements ReviewDAO {
    private static final Logger logger = LogManager.getLogger(SQLAnimeReview.class);

    /**
     * Adds a new anime review to the database.
     *
     * @param animeReview The AnimeReview object containing the details of the review.
     * @return The AnimeReview object that was added to the database.
     * @throws DAOException If an error occurs while adding the review.
     */
    @Override
    public AnimeReview addReview(AnimeReview animeReview) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String registerUserSql = "INSERT INTO review"
                + "(user_id, anime_id, rate, comment)"
                + "values(?, ?, ?, ?);";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(registerUserSql)) {

            preparedStatement.setInt(1, animeReview.userId());
            preparedStatement.setInt(2, animeReview.animeId());
            preparedStatement.setFloat(3, animeReview.rate());
            preparedStatement.setString(4, animeReview.comment());

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                return animeReview;
            }
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.WARN, "Error in adding anime review.");
            throw new DAOException(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves an anime review from the database based on the user ID and anime ID.
     *
     * @param userId  The ID of the user who wrote the review.
     * @param animeId The ID of the anime being reviewed.
     * @return The AnimeReview object retrieved from the database.
     * @throws DAOException If an error occurs while retrieving the review.
     */
    @Override
    public AnimeReview getReview(int userId, int animeId) throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String sql = "SELECT review.*, user.login"
                + " FROM review JOIN user ON user.id = review.user_id"
                + " WHERE user_id = ? AND anime_id = ?;";

        AnimeReview animeReview = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, animeId);
            try ( ResultSet rs = preparedStatement.executeQuery() ) {
                if (rs.next()) {
                    animeReview = new AnimeReview(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(6),
                            rs.getInt(3),
                            rs.getFloat(4),
                            rs.getString(5)
                    );
                }
            }
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in getting review.");
            throw new DAOException(e.getMessage());
        }
        return animeReview;
    }
}
