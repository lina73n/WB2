package by.your_film_list.controller.command;

import by.your_film_list.controller.command.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Command is an interface that is used for processing user request.
 */
public interface Command {
    /**
     * This method is used for processing user request.
     * @param request user request for processing.
     * @return Jsp page name if the request method is GET, redirect address if POST.
     * @throws CommandException if something was wrong during request processing.
     */
    String execute(HttpServletRequest request) throws CommandException;
}
