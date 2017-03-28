package tst.contactmngr.core.util;


/**
 * String utilities
 * 
 * @author ghost
 *
 */
public class StringUtil {

    
    /**
     * Checks given string for not null and not empty (with trim)
     * 
     * @param str {@link String} string
     * 
     * @return boolean <code>true</code> fi string is not null and is not empty or <code>false</code> otherwise
     */
    public static boolean hasText(String str){
        if(str == null){
            return false;
        }
        if("".equals(str.trim())) {
            return false;
        }
        return true;
    }
    
    /**
     * Fill string with the specified symbol till the maxlength from the end
     * 
     * @param str {@link String} source string
     * @param symbol {@link String} symbol
     * @param length int length
     * 
     * @return {@link String} String
     */
    public static String rpadString(String str, String symbol, int length){
        if(!hasText(str)){
            throw new IllegalArgumentException("String required");
        }
        if(symbol == null){
            throw new IllegalArgumentException("Symbol required");
        }
        if(length <= 0){
            throw new IllegalArgumentException("Length should be greater than zero.");
        }

        StringBuilder result = new StringBuilder(str);
        while(result.length() < length){
            result.append(symbol);
        }
        
        return result.toString();
    }
    
    /**
     * Fill string with the specified symbol till the maxlength from the beginning
     * 
     * @param str {@link String} source string
     * @param symbol {@link String} symbol
     * @param length int length
     * 
     * @return {@link String} String
     */
    public static String lpadString(String str, String symbol, int length){
        if(!hasText(str)){
            throw new IllegalArgumentException("String required");
        }
        if(symbol == null){
            throw new IllegalArgumentException("Symbol required");
        }
        if(length <= 0){
            throw new IllegalArgumentException("Length should be greater than zero.");
        }

        StringBuilder result = new StringBuilder(str);
        while(result.length() < length){
            result.insert(0, symbol);
        }
        
        return result.toString();
    }
    
}
