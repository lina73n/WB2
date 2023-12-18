package by.your_film_list.controller.command.impl;

import by.your_film_list.bean.User;
import by.your_film_list.controller.JspPage;
import by.your_film_list.controller.RequestAttribute;
import by.your_film_list.controller.RequestParameter;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import by.your_film_list.service.UserService;
import by.your_film_list.service.exception.ServiceException;
import by.your_film_list.service.factory.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ProfileCommand class is an implementation of the Command interface.
 * It represents a command that handles the request to view a user's profile.
 */
public class ProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ProfileCommand.class);
    /**
     * Executes the command to view a user's profile.
     *
     * @param request the HttpServletRequest object
     * @return the name of the JSP page for displaying the user's profile
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        User user = null;
        String userId = request.getParameter(
                RequestParameter.USER_ID.name().toLowerCase());
        if (userId != null) {
            int id = Integer.parseInt(request.getParameter(
                    RequestParameter.USER_ID.name().toLowerCase()));
            UserService userService = ServiceFactory.getInstance().getUserService();
            try {
                user = userService.getUser(id);
            } catch (ServiceException e) {
                throw new CommandException(e.getMessage());
            }
        }
        if (user == null) {
            throw new CommandException("Unknown user id!");
        }
        request.setAttribute(RequestAttribute
                .CURRENT_USER.name().toLowerCase(), user);
        return JspPage.PROFILE.getName();
    }
}
