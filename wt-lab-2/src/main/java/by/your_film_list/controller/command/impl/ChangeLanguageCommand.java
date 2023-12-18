package by.your_film_list.controller.command.impl;

import by.your_film_list.controller.RequestParameter;
import by.your_film_list.controller.SessionAttribute;
import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.exception.CommandException;
import by.your_film_list.view.Language;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ChangeLanguageCommand class is an implementation of the Command interface.
 * It handles the request to change the language and updates the language attribute in the session.
 */
public class ChangeLanguageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeLanguageCommand.class);
    /**
     * Executes the command to change the language and updates the language attribute in the session.
     *
     * @param request the HttpServletRequest object
     * @return the name of the page to redirect to
     * @throws CommandException if an error occurs while executing the command
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String languageName = request.getParameter(RequestParameter
                .LANGUAGE.name().toLowerCase());
        String pageName = request.getParameter(RequestParameter
                .CURR_PAGE_NAME.name().toLowerCase());

        if (languageName == null || pageName == null) {
            logger.info("Uncorrected parameters in changing language");
            throw new CommandException("Change language parameters are incorrect!");
        }

        Language language = Language.valueOf(languageName.toUpperCase());
        HttpSession httpSession = request.getSession();
        logger.info("Setting language = {}", languageName);
        httpSession.setAttribute(SessionAttribute.LANGUAGE.getName(), language);
        return pageName;
    }
}
