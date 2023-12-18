package by.your_film_list.controller.command.impl;

import by.your_film_list.controller.JspPage;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The LoginCommand class is an implementation of the Command interface.
 * It handles the request to navigate to the login page.
 */
public class LoginCommand implements Command {
    /**
     * Executes the command to navigate to the login page.
     *
     * @param request the HttpServletRequest object
     * @return the name of the login page JSP
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return JspPage.LOGIN_PAGE.getName();
    }
}
