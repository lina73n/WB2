package by.your_film_list.controller.command.impl;

import by.your_film_list.controller.JspPage;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The RegisterCommand class is an implementation of the Command interface.
 * It represents a command that handles the request to navigate to the registration page.
 */
public class RegisterCommand implements Command {
    /**
     * Executes the command to navigate to the registration page.
     *
     * @param request the HttpServletRequest object
     * @return the name of the JSP page for the registration page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return JspPage.REGISTER_PAGE.getName();
    }
}
