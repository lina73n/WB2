package by.your_film_list.controller.command.impl;

import by.your_film_list.controller.RedirectAddress;
import by.your_film_list.controller.RequestParameter;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The NextAnimeCommand class is an implementation of the Command interface.
 * It handles the request to navigate to the next page of anime.
 */
public class NextAnimeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(NextAnimeCommand.class);
    /**
     * Executes the command to navigate to the next page of anime.
     *
     * @param request the HttpServletRequest object
     * @return the address of the anime list page with the updated page number parameter
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String prevPageNumStr = request.getParameter(
                RequestParameter.PAGE_NUM.name().toLowerCase());

        int prevPageNum = Integer.parseInt(prevPageNumStr);
        int newPageNum = prevPageNum + 1;

        return RedirectAddress.ANIME_LIST.getAddress() + "&"
                + RequestParameter.PAGE_NUM.name().toLowerCase() + "=" + newPageNum;
    }
}
