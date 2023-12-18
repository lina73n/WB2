package by.your_film_list.service.impl;

import by.your_film_list.bean.Anime;
import by.your_film_list.dao.AnimeDAO;
import by.your_film_list.dao.exception.DAOException;
import by.your_film_list.dao.factory.DAOFactory;
import by.your_film_list.service.AnimeService;
import by.your_film_list.service.exception.ServiceException;
import java.util.List;

/**
 * The AnimeServiceImpl class is an implementation of the AnimeService interface.
 * It provides methods for managing anime data, such as adding anime, retrieving anime list,
 * getting anime by ID, getting anime list by page number, and getting the maximum page number.
 */
public class AnimeServiceImpl implements AnimeService {
    /**
     * The number of anime items to display on each page for pagination.
     */
    private static final int N_ANIME_ON_PAGE = 8;

    /**
     * Adds an anime to the database.
     *
     * @param anime the anime to add
     * @return true if the anime was added successfully, false otherwise
     * @throws ServiceException if an error occurs while adding the anime
     */
    @Override
    public boolean addAnime(Anime anime) throws ServiceException {
        boolean result;
        AnimeDAO animeDAO = DAOFactory.getInstance().getAnimeDAO();
        try {
            result = animeDAO.addAnime(anime);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return result;
    }

    /**
     * Retrieves a list of all animes from the database.
     *
     * @return a list of all animes
     * @throws ServiceException if an error occurs while retrieving the animes
     */
    @Override
    public List<Anime> getAnimeList() throws ServiceException {
        List<Anime> result;
        AnimeDAO animeDAO = DAOFactory.getInstance().getAnimeDAO();
        try {
            result = animeDAO.getAnime();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return result;
    }

    /**
     * Retrieves a list of animes by page number.
     *
     * @param pageNumber the page number to retrieve
     * @return a list of animes on the specified page
     * @throws ServiceException if an error occurs while retrieving the animes
     */
    @Override
    public List<Anime> getAnimeList(int pageNumber) throws ServiceException {
        int nAnime;
        AnimeDAO animeDAO = DAOFactory.getInstance().getAnimeDAO();

        if (pageNumber < 1) {
            throw new ServiceException("Page number > 0");
        }

        try {
            nAnime = animeDAO.getAnimeCount();
        } catch ( DAOException e ) {
            throw new ServiceException(e.getMessage());
        }

        int offset = (pageNumber - 1)*N_ANIME_ON_PAGE;
        if (offset >= nAnime) {
            throw new ServiceException("Offset > nAnime");
        }

        List<Anime> result;
        try {
            result = animeDAO.getAnime(offset, N_ANIME_ON_PAGE);
        } catch ( DAOException e ) {
            throw new ServiceException(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves the maximum page number for pagination.
     *
     * @return the maximum page number
     * @throws ServiceException if an error occurs while retrieving the maximum page number
     */

    @Override
    public int getMaxPageNum() throws ServiceException {
        int res;
        AnimeDAO animeDAO = DAOFactory.getInstance().getAnimeDAO();
        try {
            res = animeDAO.getAnimeCount();
        } catch ( DAOException e ) {
            throw new ServiceException(e.getMessage());
        }
        return (res - 1)/N_ANIME_ON_PAGE + 1;
    }

    /**
     * Retrieves an anime by its ID.
     *
     * @param id the ID of the anime to retrieve
     * @return the anime with the specified ID
     * @throws ServiceException if an error occurs while retrieving the anime
     */
    @Override
    public Anime getAnime(int id) throws ServiceException {
        Anime anime;
        AnimeDAO animeDAO = DAOFactory.getInstance().getAnimeDAO();
        try {
            anime = animeDAO.getAnime(id);
        } catch ( DAOException e ) {
            throw new ServiceException(e.getMessage());
        }
        return anime;
    }
}
