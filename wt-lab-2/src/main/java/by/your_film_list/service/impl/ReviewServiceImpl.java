package by.your_film_list.service.impl;

import by.your_film_list.bean.AnimeReview;
import by.your_film_list.dao.AnimeDAO;
import by.your_film_list.dao.ReviewDAO;
import by.your_film_list.dao.exception.DAOException;
import by.your_film_list.dao.factory.DAOFactory;
import by.your_film_list.service.ReviewService;
import by.your_film_list.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * This class is an implementation of the ReviewService interface.
 * It provides methods to retrieve and add anime reviews, as well as
 * get a specific review for a user and anime.
 */
public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

    /**
     * Retrieves a specific review for a user and anime.
     *
     * @param userId  the ID of the user
     * @param animeId the ID of the anime
     * @return the AnimeReview object representing the review
     * @throws ServiceException if an error occurs while retrieving the review
     */
    @Override
    public AnimeReview getReview(int userId, int animeId) throws ServiceException {
        AnimeReview animeReview;
        ReviewDAO reviewDAO = DAOFactory
                .getInstance().getReviewDAO();

        try {
            animeReview = reviewDAO.getReview(userId, animeId);
        } catch (DAOException e) {
            logger.log(Level.TRACE, "Error in DAO getReview method.");
            throw new ServiceException(e.getMessage());
        }
        return animeReview;
    }

    /**
     * Adds a new anime review.
     *
     * @param animeReview the AnimeReview object representing the review to be added
     * @return the AnimeReview object representing the added review
     * @throws ServiceException if an error occurs while adding the review
     */
    @Override
    public AnimeReview addReview(AnimeReview animeReview) throws ServiceException {
        AnimeReview result;
        ReviewDAO reviewDAO = DAOFactory
                .getInstance()
                .getReviewDAO();

        try {
            result = reviewDAO.addReview(animeReview);
        } catch (DAOException e) {
            logger.log(Level.TRACE, "Error in DAO addReview method");
            throw new ServiceException(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves all reviews for a specific anime.
     *
     * @param animeId the ID of the anime
     * @return a list of AnimeReview objects representing the reviews
     * @throws ServiceException if an error occurs while retrieving the reviews
     */
    @Override
    public List<AnimeReview> getAnimeReviews(int animeId) throws ServiceException {
        List<AnimeReview> result;
        AnimeDAO animeDAO = DAOFactory.getInstance().getAnimeDAO();
        try {
            result = animeDAO.getAnimeReviews(animeId);
        } catch (DAOException e) {
            logger.log(Level.TRACE, "Error in DAO getAnimeReviews method");
            throw new ServiceException(e.getMessage());
        }
        return result;
    }
}
