package tst.contactmngr.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Contact Manager configuration
 * @author ghost
 *
 */
@Component("contactMngrConfig")
public class ContactManagerConfig {

    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(getClass());
    
    /**
     * Default page size
     */
    public static final int DEFAULT_PAGE_SIZE = 10; 
    
    /**
     * Default page size
     */
    private int defaultPageSize = DEFAULT_PAGE_SIZE;
    
    /**
     * Project version
     */
    private String version = null;

    /**
     * Returns default page size
     * @return int default page size
     */
    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    /**
     * Sets default page size
     * @param defaultPageSize default page size
     */
    public void setDefaultPageSize(int defaultPageSize) {
        if(defaultPageSize <= 0){
            logger.error("Invalid default page size value: '" + defaultPageSize + "', value of '" + DEFAULT_PAGE_SIZE + "' will be used.");
            this.defaultPageSize = DEFAULT_PAGE_SIZE;
        }
        this.defaultPageSize = defaultPageSize;
    }

    /**
     * Returns project version
     * 
     * @return {@link String} project version
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * Sets project version
     * 
     * @param version {@link String} project version
     */
    public void setVersion(String version) {
        this.version = version;
    }

}


