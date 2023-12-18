package by.your_film_list.controller;

import by.your_film_list.controller.command.Command;
import by.your_film_list.controller.command.CommandHelper;
import by.your_film_list.controller.command.exception.CommandException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;

/**
 * This is the Controller class which acts as the main servlet for handling requests and responses in the application.
 * It is responsible for processing GET and POST requests, executing commands, and dispatching the appropriate pages.
 * The class also initializes the JDBC driver and handles exceptions that may occur during request processing.
 */
@WebServlet("/controller")
@MultipartConfig
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    /**
     * Initializes the JDBC driver.
     */
    @Override
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "JDBC driver class not found.");
        }
    }

    /**
     * Handles GET requests by processing the request, logging relevant information, and dispatching the appropriate page.
     * If an exception occurs during request processing, it redirects to the error page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, request.getRequestURI());
        logger.log(Level.DEBUG, "GET url: {}", request.getRequestURL().toString());
        String pageName;
        try {
            pageName = processRequest(request, response);
        } catch ( Exception e ) {
            logger.log(Level.WARN, "Get request process exception.");
            pageName = JspPage.ERROR_PAGE.getName();
        }

        RequestProcessor requestProcessor = new RequestProcessor();
        requestProcessor.process(request);

        logger.log(Level.DEBUG, "Page name: {}", pageName);
        RequestDispatcher dispatcher = request.getRequestDispatcher(pageName);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            errorMessageDirectlyFromResponse(response);
        }
    }

    /**
     * Handles POST requests by processing the request, logging relevant information, and redirecting to the appropriate page.
     * If an exception occurs during request processing, it redirects to the error page.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.DEBUG, "POST url: {}", request.getRequestURL().toString());
        String redirectAddress;
        try {
            redirectAddress = processRequest(request, response);
        } catch ( Exception e ) {
            logger.log(Level.DEBUG, "Error message: {}", e.getMessage());
            redirectAddress = RedirectAddress.ERROR.getAddress();
        }

        logger.log(Level.DEBUG, "Redirect addr: {}", redirectAddress);
        response.sendRedirect(redirectAddress);
    }

    /**
     * Processes the request by getting the command name, retrieving the corresponding command,
     * and executing it. Returns the page name to be dispatched.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @return the page name to be dispatched
     * @throws CommandException if an error occurs during command execution
     */
    private String processRequest(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String commandName = request.getParameter(RequestParameter
                        .COMMAND.name().toLowerCase());
        logger.log(Level.INFO, "Current command name = " + commandName);
        Command command = CommandHelper.getInstance().getCommand(commandName);
        return command.execute(request);
    }

    /**
     * Writes an error message directly to the response.
     *
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    private void errorMessageDirectlyFromResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("E R R O R");
    }
}
