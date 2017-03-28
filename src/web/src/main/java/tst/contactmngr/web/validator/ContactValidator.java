package tst.contactmngr.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import tst.contactmngr.core.vo.Contact;
import tst.contactmngr.web.constants.WebConstants;

/**
 * Contact validator
 * 
 * @author ghost
 *
 */
public class ContactValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return Contact.class.equals(clazz);
    }

    @Override
    public void validate(Object commandObject, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", WebConstants.ERROR_CODE_MISSING_FIELD);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", WebConstants.ERROR_CODE_MISSING_FIELD);
        
        
    }

}
