package tst.contactmngr.web.util;

/**
 * Utilities for web module
 * 
 * @author ghost
 *
 */
public class WebUtil {

    /**
     * Line break separator for the stacktrace output
     */
    private static final String STACK_TRACE_SEPARATOR = "<br/>";
    
    /**
     * Line ident separator for the stacktrace output
     */
    private static final String STACK_TRACE_IDENT= "&nbsp;&nbsp;&nbsp;&nbsp;";
    
    /**
     * Returns stack trace
     * Linebreak symbol is: "&lt;br/&gt;".
     * Recursevely proceses stacktrace
     * 
     * @param aThrowable - throwable
     * @param depth - stacktrace depth.
     * 
     * @return - {@link String} - stacktrace as string
     */
    public static String getStackTrace(Throwable aThrowable, int depth) {
        final String NEW_LINE = STACK_TRACE_SEPARATOR;
        final String IDENT = STACK_TRACE_IDENT;
        final StringBuilder result = new StringBuilder("");
        if(depth == 0){
            result.append(aThrowable.toString());
            result.append(NEW_LINE);
            result.append(NEW_LINE);
        }
        for (StackTraceElement element : aThrowable.getStackTrace() ){
          result.append(IDENT); 
          result.append(element);
          result.append(NEW_LINE);
        }
        Throwable cause = aThrowable.getCause();
        if(cause != null){
            result.append(NEW_LINE);
            result.append("Caused by: ");
            result.append(cause.toString());
            result.append(NEW_LINE);
            result.append(getStackTrace(cause, depth-1));
        }
        return result.toString();
      }
}
