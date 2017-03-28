package tst.contactmngr.core.exception;

/**
 * There is no such Object excepion
 *  
 * @author ghost
 *
 */
public class NoSuchObjectException extends Exception {

    /**
     * Constructor with message
     * 
     * @param message {@link String} - message
     */
    public NoSuchObjectException(String message) {
        super(message);
    }
    
    /**
     * Constructor with cause
     * 
     * @param cause {@link Exception} - cause
     */
    public NoSuchObjectException(Exception cause) {
        super(cause);
    }
    
    /**
     * Constructor with exception and message
     * 
     * @param message {@link String} - message
     * @param cause {@link Exception} - cause
     */
    public NoSuchObjectException(String message, Exception cause) {
        super(message, cause);
    }
}
