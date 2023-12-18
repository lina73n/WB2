package by.your_film_list.dao.factory;

import by.your_film_list.dao.AnimeDAO;
import by.your_film_list.dao.ReviewDAO;
import by.your_film_list.dao.UserDAO;
import by.your_film_list.dao.impl.SQLAnimeDAO;
import by.your_film_list.dao.impl.SQLAnimeReview;
import by.your_film_list.dao.impl.SQLUserDAO;

/**
 * Factory of different DAO objects.
 */
public final class DAOFactory {
    /**
     * Instance of DAOFactory. Used for singleton pattern.
     */
    private static final DAOFactory instance = new DAOFactory();

    /**
     * User DAO object instance.
     */
    private final UserDAO userImpl = new SQLUserDAO();
    /**
     * Anime DAO object instance.
     */
    private final AnimeDAO animeImpl = new SQLAnimeDAO();
    /**
     * Review DAO object instance.
     */
    private final ReviewDAO reviewImpl = new SQLAnimeReview();

    /**
     * Default private constructor for singleton pattern.
     */
    private DAOFactory() {
    }

    /**
     * Method for getting instance of the factory.
     * @return DAOFactory object.
     */
    public static DAOFactory getInstance() {
        return instance;
    }

    /**
     * Method for getting User DAO instance.
     * @return UserImpl object.
     */
    public UserDAO getUserDAO() {
        return userImpl;
    }

    /**
     * Method for getting Anime DAO instance.
     * @return AnimeImpl object.
     */
    public AnimeDAO getAnimeDAO() {
        return animeImpl;
    }

    /**
     * Method for getting Review DAO instance.
     * @return ReviewImpl object.
     */
    public ReviewDAO getReviewDAO() {
        return reviewImpl;
    }
}
