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
 * The BanCommand class is an implementation of the Command interface.
 * It handles the request to ban a user and redirects to the user's profile page.
 */
public class BanCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BanCommand.class);
    /**
     * Executes the command to ban a user and redirects to the user's profile page.
     *
     * @param request the HttpServletRequest object
     * @return the address to redirect to
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String userIdStr = request.getParameter(RequestParameter.USER_ID.name().toLowerCase());
        int userId = Integer.parseInt(userIdStr);
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            userService.ban(userId);
        } catch (ServiceException e) {
            logger.warn("Exception in user banning: {}", e.getMessage());
            throw new CommandException(e.getMessage());
        }
        logger.info("User successfully banned.");
        return RedirectAddress.PROFILE.getAddress() + "&"
                + RequestParameter.USER_ID.name().toLowerCase() + "=" + userId;
    }
}
