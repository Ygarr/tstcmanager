package tst.contactmngr.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import tst.contactmngr.core.dao.JdbcDao;
import tst.contactmngr.core.exception.JdbcException;
import tst.contactmngr.core.util.StringUtil;

/**
 * Abstract JDBC DAO
 * 
 * @author ghost
 *
 */
public class JdbcDaoImpl implements JdbcDao{

    /**
     * JDBC template
     */
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    /**
     * Initialization
     * 
     * @param dataSource {@link DataSource}
     */
    @Autowired
    protected void init(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate (dataSource);
        
        
    }

    /**
     * Returns jdbc template
     * 
     * @return {@link NamedParameterJdbcTemplate } - jdbc template
     */
    protected NamedParameterJdbcTemplate getJdbcTemplate(){
        return this.jdbcTemplate;
    }
    
    
    /**
     * Returns new {@link Long} ID from the specified sequence
     * 
     * @param sequenceName {@link String} - sequence name
     * 
     * @return {@link Long} - new ID
     * 
     * @throws JdbcException on failure
     */
    protected Long getNewLongId(String sequenceName) throws JdbcException {
        if(!StringUtil.hasText(sequenceName)){
            throw new IllegalArgumentException("Sequence name required");
        }
        Long result = null;
        StringBuilder query = new StringBuilder();
        query.append("call next value for ");
        query.append(sequenceName);

        try{
            MapSqlParameterSource params = null;
//            params.addValue("nextval", QueryUtil.convertToQueryParam(nextVal.toString()));
            result = getJdbcTemplate().queryForLong(query.toString(), params);
        } catch(Exception e){
            throw new JdbcException("Failed to get next sequence value for seq '" + sequenceName + "'", e);
        }
//        
        return result;
    }

    public SqlRowSet queryForRowSet(String query, MapSqlParameterSource paramMap) throws JdbcException{
        SqlRowSet rs = getJdbcTemplate().queryForRowSet(query, paramMap);
        
        return rs;
    }

    public List<Map<String, Object>> queryForList(String query, MapSqlParameterSource paramMap) throws JdbcException{
        List<Map<String,Object>> rs = getJdbcTemplate().queryForList(query, paramMap);
        
        return rs;
    }

    public int queryForCount(String query, MapSqlParameterSource paramMap) throws JdbcException{
        int result = 0;
        try {
            result = getJdbcTemplate().queryForInt(query, paramMap);
        } catch (Exception e){
            throw new JdbcException("Failed to execute count query", e);
        }
        return result;
    }
    
}
