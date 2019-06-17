package com.arvato.cc.dao1;

import com.arvato.jdf.dao.Page;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 13-1-5
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class JdbcTemplateExtend extends JdbcTemplate
{
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateExtend.class);

    @Autowired
    private DataSource dataSource;

    public DataSource getDataSource()
    {
        return dataSource;
    }
    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
        super.setDataSource(dataSource);
    }
    /**
     * 默认构造器
     */
    public JdbcTemplateExtend()
    {

    }
    /**
     * 初始构造器
     * @param dataSource
     * 数据源
     */
    public JdbcTemplateExtend(DataSource dataSource)
    {
        this.dataSource = dataSource;
        super.setDataSource(dataSource);
    }
    /**
     * 普通分页查询<br>
     * <b>如果结果结合比较大应该调用setFetchsize() 和setMaxRow两个方法来控制一下，否则会内存溢出</b>
     * @see #setFetchSize(int)
     * @see #setMaxRows(int)
     * @param sql
     * 查询的sql语句
     * @return
     * @throws DataAccessException
     */
    @SuppressWarnings("unchecked")
    public Page<?> querySP(final Page page,final String sql,final Object... values)
            throws DataAccessException
    {
        logger.info("Entry method querySP---");
        int startRow = page.getStartIndex() + 1;
        int rowsCount = page.getPageSize();
        logger.info("param sql:" + sql );
        logger.info("param pageStartIndex:" + startRow );
        logger.info("param pageRowSize:" + rowsCount );
        logger.info(" -- Execute method query by page -- ");
        List rows = (List) query(sql, new SplitPageResultSetExtractor(getColumnMapRowMapper(), startRow,rowsCount));
        if(page.isAutoCount()){
            page.setRecordCount(countSqlResult(sql,values));
        }
        page.setResult(rows);
        logger.info("Exit method querySP---");
        return page;
    }


    public List query(final String sql){
         return super.queryForList(sql);
    }

    /*
     * 组装sql语句查询Total count
     */
    private String prepareCountSql(String sql) {
        String fromHql = sql;
        //select子句与order by子句会影响count查询,进行简单的排除.
        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");
        String countHql = "select count(*) " + fromHql;
        return countHql;
    }

    /*
     * 返回page 的Total count
     */
    private long countSqlResult(final String sql, final Object... values) {
        logger.info("Enter method countSqlResult -- ");
        String countSql = prepareCountSql(sql);
        try {
            Long count = super.queryForLong(countSql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("sql can't be auto count, sql is:" + countSql, e);
        }finally {
            logger.info("Exit mthod countSqlResult -- ");
        }
    }

}

