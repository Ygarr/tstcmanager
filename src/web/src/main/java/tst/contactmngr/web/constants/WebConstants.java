package tst.contactmngr.web.constants;

/**
 * Web constants
 * 
 * @author ghost
 *
 */
public interface WebConstants {


/* REQUEST PARAMS*/
    public static final String REQUEST_PARAM_ID                       = "id";
    public static final String REQUEST_PARAM_LANG                     = "lang";
    public static final String REQUEST_PARAM_NAME                     = "name";
    public static final String REQUEST_PARAM_PAGE                     = "page";
    public static final String REQUEST_PARAM_PAGE_SIZE                = "page_size";

    
/* MODEL PARAMS*/
    public static final String MODEL_ATTR_CONFIG                      = "projectConfig";
    public static final String MODEL_ATTR_REQ_PARAMS                  = "reqParams";
    public static final String MODEL_ATTR_TIMESTAMP                   = "tstamp";
    public static final String MODEL_ATTR_CONTACT_LIST                = "contactList";
    public static final String MODEL_ATTR_SEARCH_CRITERIA             = "searchCriteria";
    public static final String MODEL_ATTR_PAGER                       = "pager";
    public static final String MODEL_ATTR_COMMAND_OBJECT              = "commandObject";
    
    public static final String MODEL_ATTR_ERROR                       = "error";

/* MAPPINGS */
    public static final String MAP_CONTACT_LIST                      = "/contact_list";
    public static final String MAP_CONTACT_SEARCH                    = "/contact_search";
    public static final String MAP_CONTACT_EDIT                      = "/contact_edit";
    public static final String MAP_CONTACT_DELETE                    = "/contact_delete";

    
/* VIEWS */
    public static final String VIEW_CONTACT_LIST                      = "/contact/contact_list";
    public static final String VIEW_CONTACT_EDIT                      = "/contact/contact_edit";
    public static final String VIEW_ERROR                             = "/error";
    
    
/* ERROR CODES */
    
    public static final String ERROR_CODE_UNKNOWN_ERROR               = "error.unknown_error";
    public static final String ERROR_CODE_MISSING_PARAMETER           = "error.missing_required_param";
    public static final String ERROR_CODE_OBJECT_NOT_FOUND_BY_ID      = "error.oject_not_found_by_id";
    public static final String ERROR_CODE_MISSING_FIELD               = "error.missing_required_field";
    public static final String ERROR_CODE_CONTACT_ALREADY_EXISTS      = "error.contact_already_exists";
    
}
