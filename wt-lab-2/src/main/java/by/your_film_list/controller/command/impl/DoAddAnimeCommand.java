package by.your_film_list.controller.command.impl;

import by.your_film_list.bean.Anime;
import by.your_film_list.controller.RedirectAddress;
import by.your_film_list.controller.RequestParameter;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import by.your_film_list.service.AnimeService;
import by.your_film_list.service.ImageService;
import by.your_film_list.service.exception.ServiceException;
import by.your_film_list.service.factory.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

/**
 * The DoAddAnimeCommand class is an implementation of the Command interface.
 * It handles the request to add a new anime and performs the necessary operations to add the anime to the database.
 */
public class DoAddAnimeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DoAddAnimeCommand.class);
    /**
     * Executes the command to add a new anime and performs the necessary operations to add the anime to the database.
     *
     * @param request the HttpServletRequest object
     * @return the address to redirect to after adding the anime successfully
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String animeName = request.getParameter(
                RequestParameter.ANIME_NAME.name().toLowerCase());
        String authorName = request.getParameter(
                RequestParameter.AUTHOR_NAME.name().toLowerCase());
        String animeDescription = request.getParameter(
                RequestParameter.ANIME_DESCRIPTION.name().toLowerCase());

        String animeYearString = request.getParameter(
                RequestParameter.ANIME_YEAR.name().toLowerCase());
        int animeYear = Integer.parseInt(animeYearString);

        animeName = animeName.trim();
        authorName = authorName.trim();
        animeDescription = animeDescription.trim();

        String imageName;

        ImageService imageService = ServiceFactory.getInstance().getImageService();
        try {
            String directoryName = request
                    .getServletContext()
                    .getRealPath("/images/");

            Part part = request.getPart(RequestParameter.ANIME_IMAGE.name().toLowerCase());
            imageName = imageService.uploadImage(part, directoryName, animeName);
        } catch (ServiceException | ServletException | IOException e ) {
            throw new CommandException(e.getMessage());
        }

        Anime newAnime = new Anime(
                Anime.ID_STUB,
                animeName,
                authorName,
                Anime.RATING_STUB,
                imageName,
                animeYear,
                animeDescription
        );

        AnimeService animeService = ServiceFactory
                .getInstance()
                .getAnimeService();

        boolean result;
        try {
            result = animeService.addAnime(newAnime);
        } catch (ServiceException e) {
            logger.warn("Exception in adding anime: {}", e.getMessage());
            throw new CommandException(e.getMessage());
        }

        logger.info("Anime adding result = {}", result);
        return RedirectAddress.ANIME_ADD_SUCCESS.getAddress();
    }
}
