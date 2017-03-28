package tst.contactmngr.core.service.impl;

import tst.contactmngr.core.dao.JdbcDao;
import tst.contactmngr.core.exception.JdbcException;
import tst.contactmngr.core.service.Manager;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Abstract UrT manager implementation
 * @author ghost
 *
 */
public abstract class AbstractManager implements Manager {

    /**
     * {@inheritDoc}
     */
    public SqlRowSet queryForRowSet(String query, MapSqlParameterSource paramMap) throws JdbcException{
        return getDao().queryForRowSet(query, paramMap);
    }

    /**
     * {@inheritDoc}
     */
    public List<Map<String, Object>> queryForList(String query, MapSqlParameterSource paramMap) throws JdbcException{
        return getDao().queryForList(query, paramMap);
    }

    /**
     * {@inheritDoc}
     */
    public int queryForCount(String query, MapSqlParameterSource paramMap) throws JdbcException{
        return getDao().queryForCount(query, paramMap);
    }

    /**
     * Returns main DAO class for manager implementation
     * 
     * @return {@link JdbcDao} - main DAO class for manager implementation
     */
    protected abstract JdbcDao getDao();
}
