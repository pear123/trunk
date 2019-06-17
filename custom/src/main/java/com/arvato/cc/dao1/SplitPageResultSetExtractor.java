package com.arvato.cc.dao1;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 13-1-5
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
 */
public class SplitPageResultSetExtractor implements ResultSetExtractor
{
    private final int start;// 起始行号
    private final int len;// 结果集合的长度
    private final RowMapper rowMapper;// 行包装器
    public SplitPageResultSetExtractor(RowMapper rowMapper, int start, int len)
    {
        Assert.notNull(rowMapper, "RowMapper is required");
        this.rowMapper = rowMapper;
        this.start = start;
        this.len = len;
    }
    /**
     * 处理结果集合,被接口自动调用，该类外边不应该调用
     */
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException
    {
        List result = new ArrayList();
        int rowNum = 0;
        int end = start + len;
        point: while (rs.next())
        {
            ++rowNum;
            if (rowNum < start)
            {
                continue point;
            }
            else
            if (rowNum >= end)
            {
                break point;
            }
            else
            {
                result.add(this.rowMapper.mapRow(rs, rowNum));
            }
        }
        return result;
    }
}
