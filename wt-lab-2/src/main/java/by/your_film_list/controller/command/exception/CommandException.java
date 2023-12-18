package by.your_film_list.controller.command.exception;

/**
 * The CommandException class represents an exception that can occur in a command.
 * It extends the Exception class.
 */
public class CommandException extends Exception {
    /**
     * Constructs a new CommandException with the specified error message.
     *
     * @param message the error message
     */
    public CommandException(String message) {
        super(message);
    }
}
