package by.your_film_list.dao.exception;

/**
 * An exception that provides information of DAO layer error.
 */
public class DAOException extends Exception{
    /**
     * Constructs DAOException object with a given message
     * @param message message of the exception
     */
    public DAOException(String message){
        super(message);
    }
}
