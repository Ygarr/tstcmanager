package tst.contactmngr.web.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.contactmngr.core.exception.NoSuchObjectException;
import tst.contactmngr.core.exception.ObjectAlreadyExistsException;
import tst.contactmngr.core.service.ContactManager;
import tst.contactmngr.core.util.StringUtil;
import tst.contactmngr.core.vo.Contact;
import tst.contactmngr.core.vo.Pager;
import tst.contactmngr.web.constants.WebConstants;
import tst.contactmngr.web.exception.ErrorDetails;
import tst.contactmngr.web.validator.ContactValidator;

/**
 * Portal controller
 * 
 * @author ghost
 *
 */
@Controller
public class ContactController extends MainController {

    /**
     * Contact manager
     */
    @Autowired
    private ContactManager contactManager;
    
    
    /**
     * Contact list page handler
     * 
     * @param model {@link Model} model
     * @param request {@link HttpServletRequest} servlet request
     * 
     * @return {@link String} view name for the contact list page
     */
    @RequestMapping(value = {"", "/", WebConstants.MAP_CONTACT_LIST}, method=RequestMethod.GET)
    public String contactListPage(Model model
                                , HttpServletRequest request
                                ){
        getLogger().debug("ContactPagedListPage");
        preRequest(model, request);
        
        int defaultPage = getSessionIntAttribute(WebConstants.REQUEST_PARAM_PAGE, Pager.DEFAULT_PAGE_NUM, request);
        int defaultPageSize = getSessionIntAttribute(WebConstants.REQUEST_PARAM_PAGE_SIZE, getConfig().getDefaultPageSize(), request);
        
        int page = ServletRequestUtils.getIntParameter(request, WebConstants.REQUEST_PARAM_PAGE, defaultPage);
        int pageSize = ServletRequestUtils.getIntParameter(request, WebConstants.REQUEST_PARAM_PAGE_SIZE, defaultPageSize);
        Pager pager = new Pager(page, pageSize);

        try {
            List<Contact> contactList = contactManager.getContactPagedList(pager);
            model.addAttribute(WebConstants.MODEL_ATTR_CONTACT_LIST, contactList);
            model.addAttribute(WebConstants.MODEL_ATTR_PAGER, pager);
            setSessionAttribute(WebConstants.REQUEST_PARAM_PAGE, pager.getPageNum(), request);
            setSessionAttribute(WebConstants.REQUEST_PARAM_PAGE_SIZE, pager.getPageSize(), request);
        } catch (Exception e) {
            getLogger().error("Failed to get contact list", e);
            String msg = getMessage(WebConstants.ERROR_CODE_UNKNOWN_ERROR);
            model.addAttribute(WebConstants.MODEL_ATTR_ERROR, new ErrorDetails(msg, e));
        }
        
        return WebConstants.VIEW_CONTACT_LIST;
    }
    
    /**
     * Contact search action handler
     * 
     * @param model {@link Model} model
     * @param request {@link HttpServletRequest} servlet request
     * 
     * @return {@link String} view name for the contact search results page
     */
    @RequestMapping(value = WebConstants.MAP_CONTACT_SEARCH)
    public String contactSearch(Model model, HttpServletRequest request){
        getLogger().debug("ContactSearch");
        preRequest(model, request);
        String searchCriteria = ServletRequestUtils.getStringParameter(request, WebConstants.REQUEST_PARAM_NAME, "");
        List<Contact> contactList = null;
        if(!StringUtil.hasText(searchCriteria)){
            String errMsg = getMessage(WebConstants.ERROR_CODE_MISSING_PARAMETER, new Object[]{WebConstants.REQUEST_PARAM_NAME});
            ErrorDetails error = new ErrorDetails(errMsg);
            model.addAttribute(WebConstants.MODEL_ATTR_ERROR, error);
            logger.error("Missing name criteria parameter.");
            return WebConstants.VIEW_CONTACT_LIST;
        } else {
            try {
                contactList = contactManager.getContactListByName(searchCriteria);
            } catch (Exception e) {
                getLogger().error("Failed to get contact list by criteria: '" + searchCriteria + "'", e);
                String msg = getMessage(WebConstants.ERROR_CODE_UNKNOWN_ERROR);
                model.addAttribute(WebConstants.MODEL_ATTR_ERROR, new ErrorDetails(msg, e));
            }
        }
        model.addAttribute(WebConstants.MODEL_ATTR_CONTACT_LIST, contactList);
        model.addAttribute(WebConstants.MODEL_ATTR_SEARCH_CRITERIA, searchCriteria);
        
        return WebConstants.VIEW_CONTACT_LIST;
    }

    /**
     * Show contact edit (create/update) form
     * 
     * @param model {@link Model} model
     * @param request {@link HttpServletRequest} servlet request
     * 
     * @return {@link String} view name for the contact edit form
     */
    @RequestMapping(value=WebConstants.MAP_CONTACT_EDIT, method=RequestMethod.GET)
    public String showEditContactForm(Model model, HttpServletRequest request) {
        getLogger().debug("ContactEditForm");
        preRequest(model, request);
        long id = ServletRequestUtils.getLongParameter(request, WebConstants.REQUEST_PARAM_ID, -1);
        Contact commandObject = null; 
        if(id == -1){
            // create contact
            commandObject = new Contact();
        } else {
            // edit contact
            try{
                commandObject = contactManager.getContactById(id);
            } catch (NoSuchObjectException e) {
                getLogger().error("Failed to get contact by id: '" + id + "'", e);
                String msg = getMessage(WebConstants.ERROR_CODE_OBJECT_NOT_FOUND_BY_ID, new Object[]{id});
                model.addAttribute(WebConstants.MODEL_ATTR_ERROR, new ErrorDetails(msg, e));
            } catch(Exception e){
                getLogger().error("Failed to get contact by id: '" + id + "'", e);
                String msg = getMessage(WebConstants.ERROR_CODE_UNKNOWN_ERROR);
                model.addAttribute(WebConstants.MODEL_ATTR_ERROR, new ErrorDetails(msg, e));
            } 
        }
        model.addAttribute(WebConstants.MODEL_ATTR_COMMAND_OBJECT, commandObject);
        
        return WebConstants.VIEW_CONTACT_EDIT;
        
    }

    /**
     * Submit contact edit (create/update) form
     * 
     * @param model {@link Model} model
     * @param commandObject {@link Contact} command object
     * @param result {@link BindingResult} binding result
     * @param request {@link HttpServletRequest} request
     * 
     * @return {@link String} view name for the contact edit form
     */
    @RequestMapping(value=WebConstants.MAP_CONTACT_EDIT, method=RequestMethod.POST)
    public String submitEditContactForm(Model model
                                      , @ModelAttribute("commandObject") 
                                        @Valid 
                                        Contact commandObject
                                      , BindingResult result
                                      , HttpServletRequest request) {
        getLogger().debug("ContactEditSubmit");
        preRequest(model, request);
        if (result.hasErrors()) {
            // handle validation errors
            model.addAttribute(WebConstants.MODEL_ATTR_COMMAND_OBJECT, commandObject);
            model.addAllAttributes(result.getModel());
        } else {
            // save contact
            try {
                if(commandObject.getId() == null){
                    // create contact
                    contactManager.createContact(commandObject);
                } else {
                    // update contact
                    contactManager.updateContact(commandObject);
                }
                return sendRedirect(WebConstants.MAP_CONTACT_LIST);
            } catch (ObjectAlreadyExistsException e) {
                getLogger().error("Failed to save contact.'", e);
                String msg = getMessage(WebConstants.ERROR_CODE_CONTACT_ALREADY_EXISTS);
                model.addAttribute(WebConstants.MODEL_ATTR_ERROR, new ErrorDetails(msg));
            } catch (NoSuchObjectException e) {
                getLogger().error("Failed to save contact.'", e);
                String msg = getMessage(WebConstants.ERROR_CODE_OBJECT_NOT_FOUND_BY_ID, new Object[]{commandObject.getId()});
                model.addAttribute(WebConstants.MODEL_ATTR_ERROR, new ErrorDetails(msg));
            } catch (Exception e) {
                getLogger().error("Failed to save contact.'", e);
                String msg = getMessage(WebConstants.ERROR_CODE_UNKNOWN_ERROR);
                model.addAttribute(WebConstants.MODEL_ATTR_ERROR, new ErrorDetails(msg, e));
            } 
        }
        
        return WebConstants.VIEW_CONTACT_EDIT;
        
    }

    /**
     * Delete contact action handler
     * 
     * @param model {@link Model} model
     * @param request {@link HttpServletRequest} servlet request
     * 
     * @return {@link String} view name for the contact search results page
     */
    @RequestMapping(value = WebConstants.MAP_CONTACT_DELETE, method=RequestMethod.GET)
    public String contactDelete(Model model, HttpServletRequest request){
        getLogger().debug("ContactDelete");
        preRequest(model, request);
        long id = ServletRequestUtils.getLongParameter(request, WebConstants.REQUEST_PARAM_ID, -1);
        if(id == -1){
            String errMsg = getMessage(WebConstants.ERROR_CODE_MISSING_PARAMETER, new Object[]{WebConstants.REQUEST_PARAM_ID});
            ErrorDetails error = new ErrorDetails(errMsg);
            model.addAttribute(WebConstants.MODEL_ATTR_ERROR, error);
            logger.error("Missing id parameter.");
            return WebConstants.VIEW_ERROR;
        } else {
            try {
                contactManager.deleteContact(id);
            } catch (NoSuchObjectException e) {
                getLogger().error("Failed to delete contact.'", e);
                String msg = getMessage(WebConstants.ERROR_CODE_OBJECT_NOT_FOUND_BY_ID, new Object[]{id});
                model.addAttribute(WebConstants.MODEL_ATTR_ERROR, new ErrorDetails(msg));
                return WebConstants.VIEW_ERROR;
            } catch (Exception e) {
                getLogger().error("Failed to delete contact by id: '" + id + "'", e);
                String msg = getMessage(WebConstants.ERROR_CODE_UNKNOWN_ERROR);
                model.addAttribute(WebConstants.MODEL_ATTR_ERROR, new ErrorDetails(msg, e));
                return WebConstants.VIEW_ERROR;
            } 
        }
        return sendRedirect(WebConstants.MAP_CONTACT_LIST);
    }
    
    
    /**
     * Binder initialization
     * 
     * @param binder {@link WebDataBinder} binder
     */
    @InitBinder("commandObject")
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.setValidator(new ContactValidator());
    }

}
