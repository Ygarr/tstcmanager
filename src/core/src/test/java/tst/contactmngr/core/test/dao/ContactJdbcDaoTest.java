package tst.contactmngr.core.test.dao;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import tst.contactmngr.core.dao.ContactJdbcDao;
import tst.contactmngr.core.exception.JdbcException;
import tst.contactmngr.core.exception.NoSuchObjectException;
import tst.contactmngr.core.exception.ObjectAlreadyExistsException;
import tst.contactmngr.core.test.TestUtil;
import tst.contactmngr.core.vo.Contact;
import tst.contactmngr.core.vo.Pager;

/**
 * Contact DAO tests
 * 
 * @author ghost
 *
 */
@ContextConfiguration({"classpath:/core-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="txManager", defaultRollback=true)
@Transactional
public class ContactJdbcDaoTest {

    /**
     * Contact DAO
     */
    @Autowired
    private ContactJdbcDao contactDao;

    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(getClass());
    
    /**
     * Test normal contact creation
     */
    @Test
    public void testCreateAndGetContact() {
        Contact contact = TestUtil.createRandomContact();
        try {
            long id = contactDao.createContact(contact);
            contact.setId(id);
        } catch (Exception e) {
            logger.error("Failed to create contact: " + contact, e);
            Assert.fail("Failed to create contact");
        }
        try {
            Contact fetchedContact = contactDao.getContactById(contact.getId());
            Assert.assertEquals("Saved contact differs from the created one.", contact, fetchedContact);
        } catch (Exception e) {
            logger.error("Failed to get contact by ID", e);
            Assert.fail("Failed to get contact by ID");
        }
    }
    
    /**
     * Test contact creation (unique name check)
     */
    @Test
    public void testCreateDuplicateContact() {
        Contact contact = TestUtil.createRandomContact();
        
        try {
            long id = contactDao.createContact(contact);
            contact.setId(id);
        } catch (Exception e) {
            logger.error("Failed to create contact: " + contact, e);
            Assert.fail("Failed to create contact");
        } 
        
        try {
            contactDao.createContact(contact);
        } catch (Exception e) {
            if(!(e instanceof ObjectAlreadyExistsException)){
                logger.error("Unhandelled exception: " + contact, e);
                Assert.fail("Duplicate contact creation failed.");
            }
        }
    }
    
    /**
     * Test normal contact update
     */
    @Test
    public void testUpdateContact() {
        Contact contact = TestUtil.createRandomContact();
        
        try {
            long id = contactDao.createContact(contact);
            contact.setId(id);
        } catch (Exception e) {
            logger.error("Failed to create contact: " + contact, e);
            Assert.fail("Failed to create contact");
        }
        
        try {
            contact.setName(contact.getName() + "_1");
            contact.setPhoneNumber(contact.getPhoneNumber() + "_1");
            contactDao.updateContact(contact);
        } catch (Exception e) {
            logger.error("Failed to update contact", e);
            Assert.fail("Failed to update contact");
        }
        try {
            Contact fetchedContact = contactDao.getContactById(contact.getId());
            Assert.assertEquals("Saved contact differs from the updated one.", contact, fetchedContact);
        } catch (Exception e) {
            logger.error("Failed to get contact by ID", e);
            Assert.fail("Failed to get contact by ID");
        }
    }
    
    /**
     * Test normal contact deletion
     */
    @Test
    public void testDeleteContact() {
        Contact contact = TestUtil.createRandomContact();
        
        try {
            long id = contactDao.createContact(contact);
            contact.setId(id);
        } catch (Exception e) {
            logger.error("Failed to create contact: " + contact, e);
            Assert.fail("Failed to create contact");
        }
        
        try {
            contactDao.deleteContact(contact.getId());
        } catch (Exception e) {
            logger.error("Failed to delete contact", e);
            Assert.fail("Failed to delete contact");
        }
    }
    
    /**
     * Test normal contact deletion
     */
    @Test
    public void testDeleteNonexistentContact() {
        boolean noSuchObjectExceptionCaught = false;
        try {
            contactDao.deleteContact(-100L);
        } catch(NoSuchObjectException e) {
            noSuchObjectExceptionCaught = true;
        } catch (JdbcException e) {
            logger.error("Failed to delete contact", e);
            Assert.fail("Failed to delete contact");
        }
        Assert.assertEquals("NoSuchObjectException expected.", noSuchObjectExceptionCaught, true);
    }

    /**
     * Test normal contact deletion
     */
    @Test
    public void testGetNonexistentContact() {
        boolean noSuchObjectExceptionCaught = false;
        try {
            contactDao.getContactById(-100L);
        } catch(NoSuchObjectException e) {
            noSuchObjectExceptionCaught = true;
        } catch (JdbcException e) {
            logger.error("Failed to get contact", e);
            Assert.fail("Failed to get contact");
        }
        Assert.assertEquals("NoSuchObjectException expected.", noSuchObjectExceptionCaught, true);
    }

    /**
     * Test get contact paged list
     */
    @Test
    public void testGetContactPagedList() {
        int count = 10;
        for(int i=0; i<count; i++) {
            Contact contact = TestUtil.createRandomContact();
            try {
                contactDao.createContact(contact);
            } catch (Exception e) {
                logger.error("Failed to create contact", e);
                Assert.fail("Failed on create iteration: " + i);
            }
        }
        try {
            int pageSize = 3;
            int pageNum = 1;
            Pager pager = new Pager(pageNum, pageSize);
            List<Contact> fetchedContacts = contactDao.getContactPagedList(pager);
            int totalPagerRecords = pager.getRecordCount();
            int totalRecords = contactDao.getContactCount();
            Assert.assertEquals("Invalid total pager records count", totalRecords, totalPagerRecords);
            Assert.assertEquals("Invalid fetched page size", pageSize, fetchedContacts.size());
        } catch (JdbcException e) {
            logger.error("Failed to get contact paged list", e);
            Assert.fail("Failed to get contact paged list");
        }
    }

    /**
     * Test get contact list by name
     */
    @Test
    public void testGetContactListByName() {
        String[] names = new String[]{
                  "CoNtAcT NaMe 1"
                , "another c"
                , "UPPERCASE NAME"
                , "Имя Контакта 1"
                , "еще один"
                , "ВЕРХНИЙ РЕГИСТР"
                , "~`!@#$%^&*)(%_-+={[}]:;\"'|?/,."
               };
        for(int i=0; i<names.length; i++) {
            String name = names[i];
            Contact contact = new Contact(name, String.valueOf(i));
            try {
                contactDao.createContact(contact);
            } catch (Exception e) {
                logger.error("Failed to create contact", e);
                Assert.fail("Failed on create iteration with name: '" + name + "'");
            }
        }
        for(String name : names){
            String nameToSearch = name.toUpperCase();
            try {
                List<Contact> contactList = contactDao.getContactListByName(nameToSearch.substring(3, nameToSearch.length() - 2));
                Assert.assertEquals("Contact not found by UPPER name '" + nameToSearch + "'.", 1, contactList.size());
                Assert.assertEquals("Invalid contact name", name, contactList.get(0).getName());

                nameToSearch = name.toLowerCase();
                contactList = contactDao.getContactListByName(nameToSearch.substring(3, nameToSearch.length() - 2));
                Assert.assertEquals("Contact not found by lower name '" + nameToSearch + "'.", 1, contactList.size());
                Assert.assertEquals("Invalid contact name", name, contactList.get(0).getName());
                
                nameToSearch = name;
                contactList = contactDao.getContactListByName(nameToSearch.substring(3, nameToSearch.length() - 2));
                Assert.assertEquals("Contact not found by normal name '" + nameToSearch + "'.", 1, contactList.size());
                Assert.assertEquals("Invalid contact name", name, contactList.get(0).getName());
            } catch (JdbcException e) {
                logger.error("Failed to get contact list by name '" + nameToSearch + "'", e);
                Assert.fail("Failed to get contact list by name '" + nameToSearch + "'");
            }
        }
    }

    
}
