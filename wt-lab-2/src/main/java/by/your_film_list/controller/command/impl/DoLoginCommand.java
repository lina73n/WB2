package by.your_film_list.controller.command.impl;

import by.your_film_list.bean.User;
import by.your_film_list.controller.RedirectAddress;
import by.your_film_list.controller.RequestParameter;
import by.your_film_list.controller.SessionAttribute;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import by.your_film_list.service.UserService;
import by.your_film_list.service.exception.ServiceException;
import by.your_film_list.service.factory.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The DoLoginCommand class is an implementation of the Command interface.
 * It handles the request to log in a user and performs the necessary operations to authenticate the user.
 */
public class DoLoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DoLoginCommand.class);
    /**
     * Executes the command to log in a user and performs the necessary operations to authenticate the user.
     *
     * @param request the HttpServletRequest object
     * @return the address to redirect to after successful login
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(
                RequestParameter.LOGIN.name().toLowerCase());
        String password = request.getParameter(
                RequestParameter.PASSWORD.name().toLowerCase());

        UserService userService = ServiceFactory
                .getInstance().getUserService();
        User user;
        try {
            user = userService.login(login, password);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }

        HttpSession httpSession = request.getSession();
        if (user != null) {
            httpSession.setAttribute(SessionAttribute.USER.getName(), user);
            return RedirectAddress.LOGIN_SUCCESS.getAddress();
        }

        return RedirectAddress.LOGIN_FAILED.getAddress();
    }
}
