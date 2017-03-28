package tst.contactmngr.core.exception;

/**
 * JDBC exception
 * 
 * @author ghost
 *
 */
public class JdbcException extends Exception {

    /**
     * Constructor with message
     * 
     * @param message {@link String} - message
     */
    public JdbcException(String message) {
        super(message);
    }
    
    /**
     * Constructor with cause
     * 
     * @param cause {@link Exception} - cause
     */
    public JdbcException(Exception cause) {
        super(cause);
    }
    
    /**
     * Constructor with cause and message
     * 
     * @param message {@link String} - message
     * @param cause {@link Exception} - cause
     */
    public JdbcException(String message, Exception cause) {
        super(message, cause);
    }
}
