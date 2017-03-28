package tst.contactmngr.core.service;

import java.util.List;

import tst.contactmngr.core.exception.JdbcException;
import tst.contactmngr.core.exception.NoSuchObjectException;
import tst.contactmngr.core.exception.ObjectAlreadyExistsException;
import tst.contactmngr.core.vo.Contact;
import tst.contactmngr.core.vo.Pager;

/**
 * UrT Player manager interface
 * 
 * @author ghost
 *
 */
public interface ContactManager extends Manager {

    /**
     * Creates contact record
     * 
     * @param entity {@link Contact} contact entity
     * 
     * @return long - new contact ID
     * 
     * @throws ObjectAlreadyExistsException if such contact exists
     * @throws JdbcException on failure
     */
    public long createContact(Contact entity) throws ObjectAlreadyExistsException, JdbcException;
    
    /**
     * Updates contact details
     * 
     * @param entity {@link Contact} contact entity
     * 
     * @throws JdbcException on failure
     * @throws ObjectAlreadyExistsException if such contact exists
     * @throws NoSuchObjectException if there is no such contact
     */
    public void updateContact(Contact entity) throws NoSuchObjectException, ObjectAlreadyExistsException, JdbcException;
    
    /**
     * Delete contact by ID
     * 
     * @param id long - contact ID
     * 
     * @throws NoSuchObjectException if there is no such contact
     * @throws JdbcException on failure
     */
    public void deleteContact(long id) throws NoSuchObjectException, JdbcException;

    /**
     * Returns Contact object by ID
     * 
     * @param id {@link Long} contact unique ID 
     * 
     * @return {@link Contact} contact object
     * 
     * @throws NoSuchObjectException if there is no such contact
     * @throws JdbcException on failure 
     */
    public Contact getContactById(long id) throws NoSuchObjectException, JdbcException;

    /**
     * Returns contact paged list ordered by contact name
     * 
     * @param pager {@link Pager} paging info
     * 
     * @return {@link List} of {@link Contact} objects
     * 
     * @throws JdbcException on failure 
     */
    public List<Contact> getContactPagedList(Pager pager) throws JdbcException;

    /**
     * Returns contact list search result by name.
     * 
     * @param name {@link String} contact name to search
     * 
     * @return {@link List} of {@link Contact} objects
     * 
     * @throws JdbcException on failure
     */
    public List<Contact> getContactListByName(String name) throws JdbcException;
    
    /**
     * Returns contact count
     * 
     * @return int - contact count
     * 
     * @throws JdbcException on failure
     */
    public int getContactCount() throws JdbcException;
}
