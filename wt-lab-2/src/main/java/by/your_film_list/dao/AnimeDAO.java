package by.your_film_list.dao;

import by.your_film_list.bean.Anime;
import by.your_film_list.bean.AnimeReview;
import by.your_film_list.dao.exception.DAOException;
import java.util.List;

/**
 * The AnimeDAO interface defines the methods for accessing and manipulating anime data in the database.
 */
public interface AnimeDAO {
    /**
     * Adds an anime to the database.
     *
     * @param anime the anime to add
     * @return true if the anime was added successfully, false otherwise
     * @throws DAOException if an error occurs while adding the anime
     */
    boolean addAnime(Anime anime) throws DAOException;

    /**
     * Retrieves an anime by its ID.
     *
     * @param id the ID of the anime to retrieve
     * @return the anime with the specified ID
     * @throws DAOException if an error occurs while retrieving the anime
     */
    Anime getAnime(int id) throws DAOException;

    /**
     * Retrieves a list of all animes from the database.
     *
     * @return a list of all animes
     * @throws DAOException if an error occurs while retrieving the animes
     */
    List<Anime> getAnime() throws DAOException;

    /**
     * Retrieves a list of animes with pagination.
     *
     * @param offset the starting index of the anime list
     * @param limit  the maximum number of animes to retrieve
     * @return a list of animes within the specified range
     * @throws DAOException if an error occurs while retrieving the animes
     */
    List<Anime> getAnime(int offset, int limit) throws DAOException;

    /**
     * Retrieves a list of anime reviews for a given anime ID.
     *
     * @param animeId the ID of the anime to retrieve the reviews for
     * @return a list of anime reviews for the specified anime ID
     * @throws DAOException if an error occurs while retrieving the reviews
     */
    List<AnimeReview> getAnimeReviews(int animeId) throws DAOException;

    /**
     * Retrieves the total count of animes in the database.
     *
     * @return the total count of animes
     * @throws DAOException if an error occurs while retrieving the count
     */
    int getAnimeCount() throws DAOException;
}
