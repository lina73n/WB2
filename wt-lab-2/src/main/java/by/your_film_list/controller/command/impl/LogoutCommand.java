package by.your_film_list.controller.command.impl;

import by.your_film_list.controller.JspPage;
import by.your_film_list.controller.SessionAttribute;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The LogoutCommand class is an implementation of the Command interface.
 * It handles the request to log out the user.
 */
public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);
    /**
     * Executes the command to log out the user.
     *
     * @param request the HttpServletRequest object
     * @return the name of the anime list page JSP
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SessionAttribute.USER.getName(), null);
        FilmListCommand filmListCommand = new FilmListCommand();
        filmListCommand.execute(request);
        return JspPage.ANIME_LIST.getName();
    }
}
