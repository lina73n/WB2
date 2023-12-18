package by.your_film_list.service;

import by.your_film_list.bean.Anime;
import by.your_film_list.service.exception.ServiceException;
import java.util.List;

/**
 * This is the AnimeService interface which defines the methods for managing anime data.
 * It provides functionality to add anime, retrieve anime list, get anime by id, get anime list by page number,
 * and get the maximum page number for pagination.
 */
public interface AnimeService {
    /**
     * Adds an anime to the database.
     *
     * @param anime the anime to add
     * @return true if the anime was added successfully, false otherwise
     * @throws ServiceException if an error occurs while adding the anime
     */
    boolean addAnime(Anime anime) throws ServiceException;

    /**
     * Retrieves a list of all anime's from the database.
     *
     * @return a list of all anime's
     * @throws ServiceException if an error occurs while retrieving the anime's
     */
    List<Anime> getAnimeList() throws ServiceException;

    /**
     * Retrieves an anime by its ID.
     *
     * @param id the ID of the anime to retrieve
     * @return the anime with the specified ID
     * @throws ServiceException if an error occurs while retrieving the anime
     */
    Anime getAnime(int id) throws ServiceException;

    /**
     * Retrieves a list of anime's by page number.
     *
     * @param pageNumber the page number to retrieve
     * @return a list of anime's on the specified page
     * @throws ServiceException if an error occurs while retrieving the anime's
     */
    List<Anime> getAnimeList(int pageNumber) throws ServiceException;

    /**
     * Retrieves the maximum page number for pagination.
     *
     * @return the maximum page number
     * @throws ServiceException if an error occurs while retrieving the maximum page number
     */
    int getMaxPageNum() throws ServiceException;
}
