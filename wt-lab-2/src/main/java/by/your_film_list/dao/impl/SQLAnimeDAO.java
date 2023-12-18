package by.your_film_list.dao.impl;

import by.your_film_list.bean.Anime;
import by.your_film_list.bean.AnimeReview;
import by.your_film_list.dao.AnimeDAO;
import by.your_film_list.dao.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the AnimeDAO interface and provides methods to interact with the database
 * for CRUD operations related to Anime objects.
 */
public class SQLAnimeDAO implements AnimeDAO {
    private static final Logger logger = LogManager.getLogger(SQLAnimeDAO.class);

    /**
     * Adds a new Anime object to the database.
     *
     * @param anime the Anime object to be added
     * @return true if the anime is added successfully, false otherwise
     * @throws DAOException if there is an error in inserting the anime into the database
     */
    @Override
    public boolean addAnime(Anime anime) throws DAOException {
        int nRowsAffected;
        String sql = "INSERT INTO anime(name, author, rating, image_path, year, description) " +
                "VALUES(?, ?, ?, ?, ?, ?);";

        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, anime.getName());
            preparedStatement.setString(2, anime.getAuthorName());
            preparedStatement.setFloat(3, anime.getRating());
            preparedStatement.setString(4, anime.getImagePath());
            preparedStatement.setInt(5, anime.getYear());
            preparedStatement.setString(6, anime.getDescription());

            nRowsAffected = preparedStatement.executeUpdate();
        } catch ( SQLException | InterruptedException e ) {
            logger.log(Level.WARN, "Error in inserting anime into db."); //I need to test this case, mb I need to set ERROR here
            throw new DAOException(e.getMessage());
        }

        return nRowsAffected == 1;
    }

    /**
     * Retrieves a list of all Anime objects from the database.
     *
     * @return a list of Anime objects
     * @throws DAOException if there is an error in getting the anime list from the database
     */
    @Override
    public List<Anime> getAnime() throws DAOException {
        List<Anime> anime = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String sql = "SELECT * FROM anime ORDER BY rating DESC;";

        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Anime currAnime = new Anime(rs.getInt("id"),
                                            rs.getString("name"),
                                            rs.getString("author"),
                                            rs.getFloat("rating"),
                                            rs.getString("image_path"),
                                            rs.getInt("year"),
                                            rs.getString("description")
                                            );
                anime.add(currAnime);
            }
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in getting anime list from db.");
            throw new DAOException(e.getMessage());
        }

        return anime;
    }

    /**
     * Retrieves a limited number of Anime objects from the database with pagination support.
     *
     * @param offset the starting index of the results to retrieve
     * @param limit  the maximum number of results to retrieve
     * @return a list of Anime objects
     * @throws DAOException if there is an error in getting the anime list from the database
     */
    @Override
    public List<Anime> getAnime(int offset, int limit) throws DAOException {
        List<Anime> anime = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String sql = "SELECT * FROM anime ORDER BY rating DESC LIMIT ? OFFSET ?;";

        try ( Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql) ) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            try ( ResultSet rs = preparedStatement.executeQuery() ) {
                while (rs.next()) {
                    Anime currAnime = new Anime(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getFloat("rating"),
                            rs.getString("image_path"),
                            rs.getInt("year"),
                            rs.getString("description")
                    );
                    anime.add(currAnime);
                }
            }
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in getting anime list from db.");
            throw new DAOException(e.getMessage());
        }

        return anime;
    }

    /**
     * Retrieves a specific Anime object from the database based on its ID.
     *
     * @param id the ID of the Anime object to retrieve
     * @return the Anime object with the specified ID, or null if not found
     * @throws DAOException if there is an error in getting the chosen anime from the database
     */
    @Override
    public Anime getAnime(int id) throws DAOException {
        Anime anime = null;
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String sql = "SELECT * FROM anime WHERE id = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try ( ResultSet rs = preparedStatement.executeQuery() ) {
                if (rs.next()) {
                    anime = new Anime(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getFloat("rating"),
                            rs.getString("image_path"),
                            rs.getInt("year"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in getting chosen anime from db.");
            throw new DAOException(e.getMessage());
        }

        return anime;
    }

    /**
     * Retrieves a list of AnimeReview objects for a specific Anime from the database.
     *
     * @param animeId the ID of the Anime for which to retrieve the reviews
     * @return a list of AnimeReview objects
     * @throws DAOException if there is an error in getting the anime reviews from the database
     */
    @Override
    public List<AnimeReview> getAnimeReviews(int animeId) throws DAOException {
        List<AnimeReview> reviews = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String sql = "SELECT r.id, user_id, anime_id, rate, comment, login" +
                " FROM review r JOIN user u ON r.user_id = u.id WHERE anime_id = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, animeId);
            try ( ResultSet rs = preparedStatement.executeQuery() ) {
                while ( rs.next() ) {
                    AnimeReview currAnimeReview = new AnimeReview(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("login"),
                            rs.getInt("anime_id"),
                            rs.getFloat("rate"),
                            rs.getString("comment")
                    );
                    reviews.add(currAnimeReview);
                }
            }
        } catch ( SQLException | InterruptedException e ) {
            logger.log(Level.ERROR, "Error in getting anime reviews.");
            throw new DAOException(e.getMessage());
        }

        return reviews;
    }

    /**
     * Retrieves the total count of Anime objects in the database.
     *
     * @return the count of Anime objects
     * @throws DAOException if there is an error in getting the count of anime entries from the database
     */
    @Override
    public int getAnimeCount() throws DAOException {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
        String sql = "SELECT COUNT(*) FROM anime;";

        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new DAOException("No count of anime entries...");
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error in getting chosen anime from db.");
            throw new DAOException(e.getMessage());
        }
    }
}
