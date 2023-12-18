package by.your_film_list.controller.command.impl;

import by.your_film_list.bean.AnimeReview;
import by.your_film_list.bean.User;
import by.your_film_list.controller.RedirectAddress;
import by.your_film_list.controller.RequestParameter;
import by.your_film_list.controller.SessionAttribute;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import by.your_film_list.service.ReviewService;
import by.your_film_list.service.UserService;
import by.your_film_list.service.exception.ServiceException;
import by.your_film_list.service.factory.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The AddReviewCommand class is an implementation of the Command interface.
 * It handles the request to add a review for an anime and redirects to the appropriate page.
 */
public class AddReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddReviewCommand.class);
    /**
     * Executes the command to add a review for an anime.
     *
     * @param request the HttpServletRequest object
     * @return the address to redirect to after adding the review
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String rateString = request.getParameter(
                RequestParameter.REVIEW_RATE.name().toLowerCase());
        float rate = Float.parseFloat(rateString);

        String comment = request.getParameter(
                RequestParameter.REVIEW_COMMENT.name().toLowerCase());

        String animeIdString = request.getParameter(
                RequestParameter.ANIME_ID.name().toLowerCase());
        int animeId = Integer.parseInt(animeIdString);

        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute(RequestParameter
                .USER.name().toLowerCase());

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory
                .getUserService();

        ReviewService reviewService = serviceFactory
                .getReviewService();
        try {
            user = userService.getUser(user.getId());
            if ( user == null ) {
                throw new CommandException("No such user in db.");
            }

            if ( user.isBanned() ) {
                throw new CommandException("Banned user can't add a review");
            }

            httpSession.setAttribute(SessionAttribute.USER.getName(),
                    user);

            AnimeReview newAnimeReview = new AnimeReview(
                    -1,
                    user.getId(),
                    user.getLogin(),
                    animeId,
                    rate,
                    comment
            );
            reviewService.addReview(newAnimeReview);
        } catch (ServiceException e) {
            logger.warn("Exception in reviewService.addReview: {}", e.getMessage());
            throw new CommandException(e.getMessage());
        }

        logger.info("Success in adding review.");
        return RedirectAddress.REVIEW_ADD_SUCCESS.getAddress() + "&"
        + RequestParameter.ID.name().toLowerCase() + "=" + animeIdString;
    }
}
