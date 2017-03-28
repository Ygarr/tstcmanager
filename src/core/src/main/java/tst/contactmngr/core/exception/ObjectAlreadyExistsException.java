package tst.contactmngr.core.exception;

/**
 * Object already exists excepion
 *  
 * @author ghost
 *
 */
public class ObjectAlreadyExistsException extends Exception {

    /**
     * Constructor with message
     * 
     * @param message {@link String} - message
     */
    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
    
    /**
     * Constructor with cause
     * 
     * @param cause {@link Exception} - cause
     */
    public ObjectAlreadyExistsException(Exception cause) {
        super(cause);
    }
    
    /**
     * Constructor with cause and message
     * 
     * @param message {@link String} - message
     * @param cause {@link Exception} - cause
     */
    public ObjectAlreadyExistsException(String message, Exception cause) {
        super(message, cause);
    }

}
