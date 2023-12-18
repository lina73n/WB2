package by.your_film_list.controller.command.impl;

import by.your_film_list.bean.Anime;
import by.your_film_list.controller.JspPage;
import by.your_film_list.controller.RequestParameter;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import by.your_film_list.service.AnimeService;
import by.your_film_list.service.exception.ServiceException;
import by.your_film_list.service.factory.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Command that is called when user wants to check anime list.
 */
public class FilmListCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FilmListCommand.class);
    /**
     * Executes the command to display the anime list on the JSP page.
     *
     * @param request the HttpServletRequest object
     * @return the name of the JSP page to display
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int pageNum = 1;
        String pageNumStr = request.getParameter(
                RequestParameter.PAGE_NUM.name().toLowerCase());
        if (pageNumStr != null) {
            pageNum = Integer.parseInt(pageNumStr);
        }

        logger.info("Getting anime on page = {}", pageNum);
        List<Anime> animeList = null;
        int maxPageNum;
        AnimeService animeService = ServiceFactory.getInstance().getAnimeService();
        try {
            animeList = animeService.getAnimeList(pageNum);
            maxPageNum = animeService.getMaxPageNum();
        } catch (ServiceException e) {
            logger.warn("Exception in getting anime list: {}", e.getMessage());
            throw new CommandException(e.getMessage());
        }

        logger.info("Success in getting anime list");
        request.setAttribute(RequestParameter.PAGE_NUM.name().toLowerCase(),
                pageNum);
        request.setAttribute(RequestParameter.MAX_PAGE_NUM.name().toLowerCase(),
                maxPageNum);
        request.setAttribute(RequestParameter.ANIME_LIST.name().toLowerCase(),
                animeList);
        return JspPage.ANIME_LIST.getName();
    }
}
