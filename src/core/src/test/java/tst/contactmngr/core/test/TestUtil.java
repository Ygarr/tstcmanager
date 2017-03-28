package tst.contactmngr.core.test;

import java.util.Random;

import tst.contactmngr.core.util.StringUtil;
import tst.contactmngr.core.vo.Contact;

/**
 * Test utilities
 * 
 * @author ghost
 *
 */
public class TestUtil {

    /**
     * Default contact name prefix
     */
    protected static final String DEFAULT_NAME_PREFIX = "name_";
    
    /**
     * Random generator
     */
    private static final Random random = new Random(System.currentTimeMillis());
    
    /**
     * Creates {@link Contact} object with random name and phone number
     * 
     * @return {@link Contact} created object
     */
    public static Contact createRandomContact() {
        return createRandomContact(DEFAULT_NAME_PREFIX);
    }

    /**
     * Creates {@link Contact} object with random name and phone number
     * 
     * @param namePrefix {@link String} name prefix
     * 
     * @return {@link Contact} created object
     */
    public static Contact createRandomContact(String namePrefix) {
        String prefix = DEFAULT_NAME_PREFIX;
        if(StringUtil.hasText(namePrefix)){
            prefix = namePrefix;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append("_");
        sb.append(getRandomInt());
        
        String phoneNumber = sb.toString();
        sb.insert(0, prefix);
        
        Contact result = new Contact(null, sb.toString(), phoneNumber);
        
        return result;
    }

    
    /**
     * Returns random int value (0..99)
     * 
     * @return new random value (0..99)
     */
    private static int getRandomInt() {
        return random.nextInt(10000);
    }
    
}
