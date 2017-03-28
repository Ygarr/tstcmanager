package tst.contactmngr.web.exception;

import tst.contactmngr.web.util.WebUtil;

/**
 * Error details holdert
 * @author ghost
 *
 */
public class ErrorDetails extends Exception {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6766846023186862095L;

    /**
     * Defaule stacktrace depth
     */
    public static final int DEFAULT_STACK_DEPTH = 10;

    /**
     * Localized error description
     */
    private String descr = "";
    
    /**
     * Stack trace as string
     */
    private String stackTraceAsString = "";

    /**
     * Parameterized constructor (for expected errors)
     * 
     * @param descr {@link String} localized error description
     */
    public ErrorDetails(String descr) {
        this.descr = descr;
    }
    
    /**
     * Parameterized constructor (for unexpected errors)
     * 
     * @param descr {@link String} localized error description
     * @param exception {@link Exception} exception
     */
    public ErrorDetails(String descr, Exception exception) {
        super(exception);
        this.descr = descr;
        if(exception != null){
            stackTraceAsString = WebUtil.getStackTrace(exception, DEFAULT_STACK_DEPTH);
        }
    }

    /**
     * Returns Localized error description
     * 
     * @return {@link String} Localized error description
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Returns stacktrace as formatted HTML text;
     * 
     * @return stacktrace as formatted HTML text;
     */
    public String getStackTraceAsString() {
        return stackTraceAsString;
    }
    
}
