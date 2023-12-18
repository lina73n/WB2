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
 * The DoRegisterCommand class is an implementation of the Command interface.
 * It handles the request to register a new user and performs the necessary operations to create the user.
 */
public class DoRegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DoRegisterCommand.class);
    /**
     * Executes the command to register a new user and performs the necessary operations to create the user.
     *
     * @param request the HttpServletRequest object
     * @return the address to redirect to after successful registration
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        User user;
        String login = request.getParameter(
                RequestParameter.LOGIN.name().toLowerCase());
        String password = request.getParameter(
                RequestParameter.PASSWORD.name().toLowerCase());
        String confirmedPassword = request.getParameter(
                RequestParameter.CONFIRM_PASSWORD.name().toLowerCase());
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            user = userService.register(login, password, confirmedPassword);
        } catch (ServiceException e) {
            logger.warn("Service register exception: {}", e.getMessage());
            throw new CommandException(e.getMessage());
        }

        if (user == null) {
            logger.info("Registration failed.");
            return RedirectAddress.REGISTRATION_FAILED.getAddress();
        }

        logger.info("Registration success.");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SessionAttribute.USER.getName(), user);
        return RedirectAddress.REGISTRATION_SUCCESS.getAddress();
    }
}
