package tst.contactmngr.core.test.service;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tst.contactmngr.core.exception.JdbcException;
import tst.contactmngr.core.service.ContactManager;
import tst.contactmngr.core.test.TestUtil;
import tst.contactmngr.core.vo.Contact;
import tst.contactmngr.core.vo.Pager;

/**
 * Contact manager tests
 * 
 * @author ghost
 *
 */
@ContextConfiguration({"classpath:/core-test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ContactManagerTest {

    /**
     * Contact manager
     */
    @Autowired
    private ContactManager contactManager;
    
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
        int countBefore = 0;
        try {
            countBefore = contactManager.getContactCount();
        } catch (JdbcException e) {
             Assert.fail("failed to get contact count");
        }
        
        try {
            long id = contactManager.createContact(contact);
            contact.setId(id);
        } catch (Exception e) {
            logger.error("Failed to create contact: " + contact, e);
            Assert.fail("Failed to create contact");
        }
        try {
            Contact fetchedContact = contactManager.getContactById(contact.getId());
            Assert.assertEquals("Saved contact differs from the created one.", contact, fetchedContact);
        } catch (Exception e) {
            logger.error("Failed to get contact by ID", e);
            Assert.fail("Failed to get contact by ID");
        }
        
        int countAfter = 0;
        try {
            countAfter = contactManager.getContactCount();
        } catch (JdbcException e) {
             Assert.fail("failed to get contact count");
        }
        
        Assert.assertEquals("Invalid contact count after creation", countBefore+1, countAfter);

    }
    
    /**
     * Test normal contact update
     */
    @Test
    public void testUpdateContact() {
        Contact contact = TestUtil.createRandomContact();
        
        try {
            long id = contactManager.createContact(contact);
            contact.setId(id);
        } catch (Exception e) {
            logger.error("Failed to create contact: " + contact, e);
            Assert.fail("Failed to create contact");
        }
        
        try {
            contact.setName(contact.getName() + "_1");
            contact.setPhoneNumber(contact.getPhoneNumber() + "_1");
            contactManager.updateContact(contact);
        } catch (Exception e) {
            logger.error("Failed to update contact", e);
            Assert.fail("Failed to update contact");
        }
        try {
            Contact fetchedContact = contactManager.getContactById(contact.getId());
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
            long id = contactManager.createContact(contact);
            contact.setId(id);
        } catch (Exception e) {
            logger.error("Failed to create contact: " + contact, e);
            Assert.fail("Failed to create contact");
        }
        
        int countBefore = 0;
        try {
            countBefore = contactManager.getContactCount();
        } catch (JdbcException e) {
             Assert.fail("failed to get contact count");
        }
        
        try {
            contactManager.deleteContact(contact.getId());
        } catch (Exception e) {
            logger.error("Failed to delete contact", e);
            Assert.fail("Failed to delete contact");
        }
        
        int countAfter = 0;
        try {
            countAfter = contactManager.getContactCount();
        } catch (JdbcException e) {
             Assert.fail("failed to get contact count");
        }
        
        Assert.assertEquals("Invalid contact count after creation", countBefore-1, countAfter);
    }

    /**
     * Test pager initialization
     */
    @Test
    public void testPager() {
        // check page size default value fallback
        Pager pager = new Pager(0, -20);
        Assert.assertEquals("Invalid page num", Pager.DEFAULT_PAGE_NUM, pager.getPageNum());
        Assert.assertEquals("Invalid page size", Pager.DEFAULT_PAGE_SIZE, pager.getPageSize());
        
        // check valid paging params
        pager = new Pager(3, 10);
        Assert.assertEquals("Invalid page num", 3, pager.getPageNum());
        Assert.assertEquals("Invalid page size", 10, pager.getPageSize());
        
        // check valid paging params
        pager.setRecordCount(35);
        Assert.assertEquals("Invalid record count", 35, pager.getRecordCount());
        Assert.assertEquals("Invalid page count", 4, pager.getPageCount());
        
        // check valid paging params
        pager.setRecordCount(30);
        Assert.assertEquals("Invalid record count", 30, pager.getRecordCount());
        Assert.assertEquals("Invalid page count", 3, pager.getPageCount());
        
        // check page number overflow
        pager = new Pager(8, 10);
        pager.setRecordCount(35);
        Assert.assertEquals("Invalid page num", 4, pager.getPageNum());
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
                contactManager.createContact(contact);
            } catch (Exception e) {
                logger.error("Failed to create contact", e);
                Assert.fail("Failed on create iteration: " + i);
            }
        }
        try {
            int pageSize = 3;
            int pageNum = 1;
            Pager pager = new Pager(pageNum, pageSize);
            List<Contact> fetchedContacts = contactManager.getContactPagedList(pager);
            int totalRecords = pager.getRecordCount();
            Assert.assertTrue("Invalid total records count", totalRecords >= count);
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
        int count = 10;
        String contactNamePrefix = "ConTaCt_nAmE_";
        for(int i=0; i<count; i++) {
            Contact contact = TestUtil.createRandomContact(contactNamePrefix + i);
            try {
                contactManager.createContact(contact);
            } catch (Exception e) {
                logger.error("Failed to create contact", e);
                Assert.fail("Failed on create iteration: " + i);
            }
        }
        try {
            List<Contact> fetchedContacts = contactManager.getContactListByName(contactNamePrefix.toUpperCase());
            Assert.assertEquals("Invalid fetched records count", count, fetchedContacts.size());
        } catch (JdbcException e) {
            logger.error("Failed to get contact paged list", e);
            Assert.fail("Failed to get contact paged list");
        }
    }

    
}
