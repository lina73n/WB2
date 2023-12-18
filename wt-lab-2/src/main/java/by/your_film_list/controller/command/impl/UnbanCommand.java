package by.your_film_list.controller.command.impl;

import by.your_film_list.controller.RedirectAddress;
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
 * The UnbanCommand class is an implementation of the Command interface.
 * It represents a command that handles the request to unban a user.
 */
public class UnbanCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UnbanCommand.class);
    /**
     * Executes the command to unban a user.
     *
     * @param request the HttpServletRequest object
     * @return the redirect address to the user's profile page
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String userIdStr = request.getParameter(RequestParameter
                .USER_ID.name().toLowerCase());
        int userId = Integer.parseInt(userIdStr);
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            userService.unban(userId);
        } catch (ServiceException e) {
            throw new CommandException("Exception in user banning!");
        }
        return RedirectAddress.PROFILE.getAddress() + "&"
                + RequestParameter.USER_ID.name().toLowerCase() + "=" + userId;
    }
}
