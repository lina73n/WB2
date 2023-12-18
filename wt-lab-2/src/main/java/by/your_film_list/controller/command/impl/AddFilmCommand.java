package by.your_film_list.controller.command.impl;

import by.your_film_list.controller.JspPage;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The AddAnimeCommand class is an implementation of the Command interface.
 * It handles the request to add an anime and returns the name of the JSP page to display.
 */
public class AddFilmCommand implements Command {
    /**
     * Executes the command to add an anime.
     *
     * @param request the HttpServletRequest object
     * @return the name of the JSP page to display
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return JspPage.ADD_ANIME.getName();
    }
}
