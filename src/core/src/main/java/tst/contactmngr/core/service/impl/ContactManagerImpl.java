package tst.contactmngr.core.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tst.contactmngr.core.dao.ContactJdbcDao;
import tst.contactmngr.core.dao.JdbcDao;
import tst.contactmngr.core.exception.JdbcException;
import tst.contactmngr.core.exception.NoSuchObjectException;
import tst.contactmngr.core.exception.ObjectAlreadyExistsException;
import tst.contactmngr.core.service.ContactManager;
import tst.contactmngr.core.vo.Contact;
import tst.contactmngr.core.vo.Pager;

/**
 * UrT Player manager implmentation
 * 
 * @author ghost
 *
 */
@Service
@Transactional(readOnly = true)
public class ContactManagerImpl extends AbstractManager implements ContactManager {

    /**
     * Contact DAO
     */
    @Autowired
    protected ContactJdbcDao contactDao;
    
    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(getClass());

    protected JdbcDao getDao() {
        return this.contactDao;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public long createContact(Contact entity) throws ObjectAlreadyExistsException, JdbcException {
        return contactDao.createContact(entity);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateContact(Contact entity) throws NoSuchObjectException, ObjectAlreadyExistsException, JdbcException {
        contactDao.updateContact(entity);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteContact(long id) throws NoSuchObjectException, JdbcException {
        contactDao.deleteContact(id);
    }

    public Contact getContactById(long id) throws NoSuchObjectException, JdbcException {
        return contactDao.getContactById(id);
    }

    public List<Contact> getContactPagedList(Pager pager) throws JdbcException {
        return contactDao.getContactPagedList(pager);
    }
    
    public List<Contact> getContactListByName(String name) throws JdbcException {
        return contactDao.getContactListByName(name);
    }

    
    public int getContactCount() throws JdbcException {
        return contactDao.getContactCount();
    }
}
