package tst.contactmngr.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import tst.contactmngr.core.dao.ContactJdbcDao;
import tst.contactmngr.core.exception.JdbcException;
import tst.contactmngr.core.exception.NoSuchObjectException;
import tst.contactmngr.core.exception.ObjectAlreadyExistsException;
import tst.contactmngr.core.util.QueryUtil;
import tst.contactmngr.core.util.StringUtil;
import tst.contactmngr.core.vo.Contact;
import tst.contactmngr.core.vo.Pager;

/**
 * Contact DAO implementation
 * 
 * @author ghost
 *
 */
@Repository
public class ContactJdbcDaoImpl extends JdbcDaoImpl implements ContactJdbcDao{

    /**
     * Table name
     */
    public static final String TABLE_NAME = "tbl_contacts";
    
    /**
     * Table alias
     */
    public static final String TABLE_ALIAS = "ct";
    
    /**
     * Sequence name
     */
    public static final String SEQUENCE_NAME = "seq_contact";
    
    
    /**
     * Table columns
     */
    protected static final String TABLE_COLUMNS = ""
          + "  " + TABLE_ALIAS + ".id            AS ct_id "
          + ", " + TABLE_ALIAS + ".name          AS ct_name "
          + ", " + TABLE_ALIAS + ".phone_number  AS ct_phone_number "
        ;
    
    /**
     * Table select query
     */
    protected static final String TABLE_SELECT = ""
            + "SELECT "
            + TABLE_COLUMNS
            + "FROM "
            + TABLE_NAME + " " + TABLE_ALIAS;
    
    /**
     * Table record count query
     */
    protected static final String TABLE_RECORD_COUNT = ""
            + "SELECT COUNT(*) FROM " + TABLE_NAME;
    
    /**
     * Select query: ID condition
     */
    protected static final String TABLE_CONDITION_ID = ""
            + " WHERE " + TABLE_ALIAS + ".id = :id ";
    
    /**
     * Select query: name condition
     */
    protected static final String TABLE_CONDITION_NAME = ""
            + " WHERE LOWER(" + TABLE_ALIAS + ".name) LIKE :name ";

    /**
     * Order by name clause
     */
    protected static final String TABLE_ORDER_BY_NAME = ""
            + " ORDER BY " + TABLE_ALIAS + ".name ";
    
    /**
     * Paging condition
     */
    protected static final String TABLE_OFFSET_AND_LIMIT = ""
            + " LIMIT :limit OFFSET :offset ";
    
    /**
     * Table insert query
     */
    protected static final String TABLE_INSERT_QUERY = ""
            + "INSERT INTO " + TABLE_NAME + "("
            + "  id "
            + ", name "
            + ", phone_number "
            + ")"
            + " VALUES ("
            + "  :id "
            + ", :name "
            + ", :phone_number "
            + ")"
          ;
    
    /**
     * Table record update by ID query
     */
    protected static final String RECORD_UPDATE_QUERY = ""
            + "UPDATE " + TABLE_NAME + " "
            + "SET "
            + "  name = :name "
            + ", phone_number = :phone_number "
            + "WHERE id = :id ";

    /**
     * Delete record by ID query
     */
    protected static final String TABLE_DELETE_QUERY = ""
            + "DELETE FROM " + TABLE_NAME
            + " WHERE id = :id ";
    
    /**
     * ContactRowMapper instance
     */
    protected static ContactRowMapper contactRowMapper = new ContactRowMapper();
    
    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(getClass());

    
    
    public long createContact(Contact entity) throws ObjectAlreadyExistsException, JdbcException {
        if(entity == null){
            throw new IllegalArgumentException("Entity required");
        }
        
        // get new ID from sequence
        long id = getNewLongId(SEQUENCE_NAME);
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", QueryUtil.convertToQueryParam(id));
        params.addValue("name", QueryUtil.convertToQueryParam(entity.getName()));
        params.addValue("phone_number", QueryUtil.convertToQueryParam(entity.getPhoneNumber()));
        
        try{
            getJdbcTemplate().update(TABLE_INSERT_QUERY, params);
            entity.setId(Long.valueOf(id));
        } catch(DuplicateKeyException e) {
            throw new ObjectAlreadyExistsException("Contact with name: '" 
                    + entity.getName() + "' and number: '" 
                    + entity.getPhoneNumber() + "' already exists.");
        } catch (Exception e){
            throw new JdbcException("Failed to create record: " + entity.toString(), e);
        }
        
        return id;
    }

    public void updateContact(Contact entity) throws NoSuchObjectException, ObjectAlreadyExistsException, JdbcException{
        if(entity.getId() == null){
            throw new IllegalArgumentException("Contact ID is null.");
        }
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", QueryUtil.convertToQueryParam(entity.getId()));
        params.addValue("name", QueryUtil.convertToQueryParam(entity.getName()));
        params.addValue("phone_number", QueryUtil.convertToQueryParam(entity.getPhoneNumber()));
        int rc = 0;
        
        try{
            rc = getJdbcTemplate().update(RECORD_UPDATE_QUERY, params);
        } catch(DuplicateKeyException e) {
            throw new ObjectAlreadyExistsException("Contact with name: '" 
                    + entity.getName() + "' and number: '" 
                    + entity.getPhoneNumber() + "' already exists.");
        } catch(Exception e){
            throw new JdbcException("Failed to update contact by ID: " + entity.getId(), e);
        }
        if(rc == 0){
            throw new NoSuchObjectException("There is no contact with ID " + entity.getId());
        }
    }

    public void deleteContact(long id) throws NoSuchObjectException, JdbcException{
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", QueryUtil.convertToQueryParam(id));
        int rc = 0;
        try{
            rc = getJdbcTemplate().update(TABLE_DELETE_QUERY, params);
            logger.debug("Deleted record count: " + rc);
        } catch (Exception e){
            throw new JdbcException("Failed to delete contact with ID: " + id, e);
        }
        if(rc == 0){
            throw new NoSuchObjectException("There is no contact with ID " + id);
        }
    }
    
    public Contact getContactById(long id) throws NoSuchObjectException, JdbcException {
        Contact contact = null;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", QueryUtil.convertToQueryParam(Long.valueOf(id)));
        try{
            contact = getJdbcTemplate().queryForObject(TABLE_SELECT + TABLE_CONDITION_ID, params, contactRowMapper);
        } catch(EmptyResultDataAccessException e){
            throw new NoSuchObjectException("There is no contact with ID " + id, e);
        } catch(Exception e){
            throw new JdbcException("Failed to get record with ID " + id, e);
        }
        return contact;
    }
    
    
    public List<Contact> getContactPagedList(Pager pager) throws JdbcException {
        if(pager == null) {
            throw new IllegalArgumentException("Pager required.");
        }
        List<Contact> result = null;
        
        int totalCount = getContactCount();
        pager.setRecordCount(totalCount);
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("offset", QueryUtil.convertToQueryParam(Integer.valueOf(pager.getRecordOffset())));
        params.addValue("limit", QueryUtil.convertToQueryParam(Integer.valueOf(pager.getPageSize())));
        try{
            result = getJdbcTemplate().query(TABLE_SELECT + TABLE_ORDER_BY_NAME + TABLE_OFFSET_AND_LIMIT, params, contactRowMapper);
        } catch (EmptyResultDataAccessException e){
            result = new ArrayList<Contact>();
        } catch(Exception e){
            throw new JdbcException("Failed to get contact paged list", e);
        }
        return result;
    }

    public List<Contact> getContactListByName(String name) throws JdbcException {
        if(!StringUtil.hasText(name)) {
            throw new IllegalArgumentException("Contact name required.");
        }
        List<Contact> result = null;
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder nameParam = new StringBuilder();
        nameParam.append("%");
        nameParam.append(name.toLowerCase());
        nameParam.append("%");
        
        params.addValue("name", QueryUtil.convertToQueryParam(nameParam.toString()));
        try{
            result = getJdbcTemplate().query(TABLE_SELECT + TABLE_CONDITION_NAME + TABLE_ORDER_BY_NAME, params, contactRowMapper);
        } catch (EmptyResultDataAccessException e){
            result = new ArrayList<Contact>();
        } catch(Exception e){
            throw new JdbcException("Failed to get contact list by name", e);
        }
        return result;
    }

    
    public int getContactCount() throws JdbcException {
        return queryForCount(TABLE_RECORD_COUNT, null);
    }
    
    /**
     * Contact row mapper
     * 
     * @author ghost
     *
     */
    protected static class ContactRowMapper implements RowMapper<Contact> {
        
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contact result = null;
            result = new Contact(
                    rs.getLong("ct_id")
                  , rs.getString("ct_name")
                  , rs.getString("ct_phone_number")
                  );
            return result;
        }
    }
    
}
