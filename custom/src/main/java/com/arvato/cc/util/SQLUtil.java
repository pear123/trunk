package com.arvato.cc.util;

import org.apache.commons.lang.StringUtils;

public class SQLUtil
{
        
    public static final String COUNT = "select count(*) ";
    
    public static String queryCount(String scope_sql){
        String countSql = "";
        
        String queryString = scope_sql.substring(scope_sql.indexOf("from"));
        
        if (queryString.startsWith("from")) {
            countSql = COUNT + queryString;
        }
        
        return countSql;
    }
    
    public static String jointFieldQuery(String page_sql, PaginatedList<Object[]> paginatedList, String queryField, String primaryKey){
        String direction = paginatedList.getSortDirection();//排序方向
        String criterion = paginatedList.getSortCriterion();//排序列
        
        //ORDER_FIELD = select (everything contains join by yourself) from table where key in ??
        
//        String primaryKey = queryField.substring(0,queryField.indexOf(","));
        
        String yeah = "select " + queryField + " where " + primaryKey + " in ( ";
        
        String field_Sql = yeah + page_sql;
        
        StringBuilder fs = new StringBuilder(field_Sql);
        
        //拼接排序条件
        if(StringUtils.isNotBlank(criterion)){
            fs.append(" ) order by ");
            fs.append(criterion);
            fs.append(" ");
            fs.append(direction);
        }
        
        return fs.toString();
    }
    
    //查询 scope_sql， 范围 - 最大阀值
    public static String jointScopeQuery(String scope_sql, PaginatedList<Object[]> paginatedList, String primaryKey){
        String direction = paginatedList.getSortDirection();//排序方向
        String criterion = paginatedList.getSortCriterion();//排序列
        int startIndex = paginatedList.getStartIndex();//起始页
        int perPage = paginatedList.getObjectsPerPage();//单页显示数
        
        scope_sql = "select rownum rownum_, " + primaryKey + " " + scope_sql;
        
        //scope_sql = select rownum rownum_, key from table where...
        StringBuilder ssq = new StringBuilder(scope_sql);
        
        //拼接范围页
        if(!Validate.isNullOrEmpty(perPage)){
            int rowLength = perPage + startIndex;
            ssq.append(" and rownum <= ");
            ssq.append(rowLength);
        }
        
        //拼接排序条件
        if(scope_sql.indexOf("order by") < 0 && StringUtils.isNotBlank(criterion)){
            ssq.append(" order by ");
            ssq.append(criterion);
            ssq.append(" ");
            ssq.append(direction);
        }
        
        return ssq.toString();
    }
    
    //查询 page_sql， 范围 - 初始阀值
    public static String jointPageQuery(String scope_sql, PaginatedList<Object[]> paginatedList, String primaryKey){
        int startIndex = paginatedList.getStartIndex();//起始页
        
        //ORDER_START_PAGE_HEAD =  "select primary_key from ( ";
        String page_Sql =  "select " + primaryKey + " from ( " + scope_sql;
        
        StringBuilder ps = new StringBuilder(page_Sql);
        
        ps.append(" ) ");
        ps.append(primaryKey.split("\\.")[0]);
        
        if(!Validate.isNullOrEmpty(startIndex)){
            
            ps.append(" where rownum_ > ");
            ps.append(startIndex);
        }
        
        return ps.toString();
    }
}



















