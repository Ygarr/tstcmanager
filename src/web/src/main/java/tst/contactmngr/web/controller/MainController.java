package tst.contactmngr.web.controller;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;

import tst.contactmngr.core.util.StringUtil;
import tst.contactmngr.web.ContactManagerConfig;
import tst.contactmngr.web.constants.WebConstants;

/**
 * Base controller class
 * 
 * @author ghost
 */
public class MainController {

    /**
     * Redirect view prefix
     */
    protected static final String REDIRECT_PREFIX = "redirect:";
    
    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(getClass());

    /**
     * Message source
     */
    @Autowired
    private MessageSource messageSource;
    
    /**
     * Project config
     */
    @Autowired
    private ContactManagerConfig contactManagerConfig;
    
    /**
     * Returns message source
     * 
     * @return {@link MessageSource} message source
     */
    protected MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * Returns project config
     * @return {@link ContactManagerConfig} config
     */
    public ContactManagerConfig getConfig() {
        return contactManagerConfig;
    }

    /**
     * Returns logger
     * 
     * @return {@link Logger} logger
     */
    protected Logger getLogger() {
        return logger;
    }
    
    /**
     * Preprocess request
     * 
     * @param model {@link Model} data model
     * @param request {@link HttpServletRequest} request
     */
    protected void preRequest(Model model, HttpServletRequest request) {
        model.addAttribute(WebConstants.MODEL_ATTR_TIMESTAMP, System.currentTimeMillis());
        model.addAttribute(WebConstants.MODEL_ATTR_CONFIG, getConfig());
        Enumeration<String> paramNames = request.getParameterNames();
        StringBuilder reqParams = new StringBuilder("");
        if(paramNames != null && paramNames.hasMoreElements()){
            while(paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                if(!WebConstants.REQUEST_PARAM_LANG.equals(paramName)){
                    String paramValue = request.getParameter(paramName);
                    if(!StringUtil.hasText(paramValue)){
                        paramValue = "";
                    }
                    if(reqParams.length() > 0){
                        reqParams.append("&");
                    }
                    reqParams.append(paramName);
                    reqParams.append("=");
                    reqParams.append(paramValue);
                }
            }
        }
        
        model.addAttribute(WebConstants.MODEL_ATTR_REQ_PARAMS, reqParams.toString());
    }

    /**
     * Returns localized message text from resource bundle
     * 
     * @param messageCode {@link String}
     * 
     * @return {@link String} localized message text from resource bundle
     */
    protected String getMessage(String messageCode){
        return getMessage(messageCode, null);
    }
    
    /**
     * Returns localized message text from resource bundle
     * 
     * @param messageCode {@link String}
     * @param args - array of {@link Object} message arguments 
     * 
     * @return {@link String} localized message text from resource bundle
     */
    protected String getMessage(String messageCode, Object[] args){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageCode, args, messageCode, locale);
    }
    
    /**
     * Returns redirect mapping
     * 
     * @param mapping {@link String} mapping to redirect to
     * 
     * @return {@link String} redirect mapping
     */
    protected String sendRedirect(String mapping) {
        if(!StringUtil.hasText(mapping)){
            throw new IllegalArgumentException("Mapping required.");
        }
        StringBuilder result = new StringBuilder(mapping);
        result.insert(0, REDIRECT_PREFIX);
            
        return result.toString();
    }
    
    /**
     * Stores session attribute
     * 
     * @param attributeName {@link String} attribute name
     * @param attributeValue {@link Object} attribute value
     * @param request {@link HttpServletRequest} request
     */
    protected void setSessionAttribute(String attributeName, Object attributeValue, HttpServletRequest request) {
        HttpSession session =  request.getSession();
        if(session != null) {
            session.setAttribute(attributeName, attributeValue);
        }
    }
    
    /**
     * Returns session attribute (or default value, if session attribute is undefined)
     * 
     * @param attributeName {@link String} attribute name
     * @param defaultValue {@link Object} default value
     * @param request {@link HttpServletRequest} request
     * @return {@link Object} session attribute value (or default value, if session attribute is undefined)
     */
    protected Object getSessionAttribute(String attributeName, Object defaultValue, HttpServletRequest request){
        Object result = null;
        HttpSession session =  request.getSession();
        if(session != null) {
            result = session.getAttribute(attributeName);
        }

        if(result == null){
            return defaultValue;
        }
        return result;
    }
    
    /**
     * Returns session attribute (or default value, if session attribute is undefined)
     * 
     * @param attributeName {@link String} attribute name
     * @param defaultValue default int value
     * @param request {@link HttpServletRequest} request
     * 
     * @return  session int attribute value (or default value, if session attribute is undefined)
     */
    protected int getSessionIntAttribute(String attributeName, int defaultValue, HttpServletRequest request){
        int result = defaultValue;
        Object value = getSessionAttribute(attributeName, defaultValue, request);
        if(value != null){
            result = ((Integer)value).intValue();
        }
        return result;
    }

}
