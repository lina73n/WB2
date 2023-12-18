package by.your_film_list.controller.command.impl;

import by.your_film_list.bean.Anime;
import by.your_film_list.bean.AnimeReview;
import by.your_film_list.bean.User;
import by.your_film_list.controller.JspPage;
import by.your_film_list.controller.RequestAttribute;
import by.your_film_list.controller.RequestParameter;
import by.your_film_list.controller.SessionAttribute;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import by.your_film_list.service.AnimeService;
import by.your_film_list.service.ReviewService;
import by.your_film_list.service.UserService;
import by.your_film_list.service.exception.ServiceException;
import by.your_film_list.service.factory.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * The ShowAnimeCommand class is an implementation of the Command interface.
 * It represents a command that handles the request to show detailed information about an anime.
 */
public class ShowAnimeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowAnimeCommand.class);
    /**
     * Executes the command to show detailed information about an anime.
     *
     * @param request the HttpServletRequest object
     * @return the name of the JSP page for displaying the anime details
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Anime anime;
        User user;
        AnimeReview userReview = null;
        List<AnimeReview> animeReviews;

        String animeIdParam = request.getParameter(
                RequestParameter.ID.name().toLowerCase());
        int animeId = Integer.parseInt(animeIdParam);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ReviewService reviewService = serviceFactory
                .getReviewService();

        AnimeService animeService = serviceFactory
                .getAnimeService();

        UserService userService = serviceFactory
                .getUserService();

        try {
            anime = animeService.getAnime(animeId);
            animeReviews = reviewService.getAnimeReviews(animeId);

            HttpSession httpSession = request.getSession();
            user = (User) httpSession.getAttribute(SessionAttribute.USER.getName());
            if ( user != null ) {
                user = userService.getUser(user.getId());
                if ( user == null ) {
                    throw new CommandException("There is no such user in db.");
                }
                httpSession.setAttribute(SessionAttribute.USER.getName(), user);
                userReview = reviewService.getReview(user.getId(), animeId);
            }
        } catch (ServiceException e) {
            logger.warn("Error in showing anime: {}", e.getMessage());
            throw new CommandException(e.getMessage());
        }

        request.setAttribute(RequestAttribute.USER_REVIEW.name().toLowerCase(),
                userReview);
        request.setAttribute(RequestAttribute.ANIME_REVIEWS.name().toLowerCase(),
                animeReviews);
        request.setAttribute(RequestAttribute.USER.name().toLowerCase(), user);
        request.setAttribute(RequestAttribute.ANIME.name().toLowerCase(), anime);
        return JspPage.ANIME.getName();
    }
}
