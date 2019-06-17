package com.arvato.cc.util;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-26
 * Time: 下午9:19
 * To change this template use File | Settings | File Templates.
 */


import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class JdbcUtil {
//    private Connection connection = null;
//    private PreparedStatement pstmt = null;
//    private ResultSet resultSet = null;

    private static JdbcUtil jdbcUtilSingle = null;

//    @Value("${driverClassName}")
//    private String driverClassName;
//
//    @Value("${url}")
//    private String url;
//
//    @Value("${username}")
//    private String username;
//
//    @Value("${password}")
//    private String password;


    public static JdbcUtil getJdbcUtilSingle(){
        if (jdbcUtilSingle == null) {
            // 给类加锁 防止线程并发
            synchronized (JdbcUtil.class) {
                if (jdbcUtilSingle == null) {
                    jdbcUtilSingle = new JdbcUtil();
                }
            }
        }
        return jdbcUtilSingle;
    }




    /**
     * 获得数据库的连接
     * @return
     */
    public Connection getConnection(){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config/jstDB.properties");
        Properties p = new Properties();
        try{
            p.load(inputStream);
        } catch (IOException e1){
            e1.printStackTrace();
        }
        Connection connection = null;
        try {
            PropertiesFileReader reader = new PropertiesFileReader();
//            Class.forName(reader.driver);
            Class.forName(p.getProperty("jst.driverClassName").toString());
//            connection = DriverManager.getConnection(PropertiesFileReader.url, PropertiesFileReader.username, PropertiesFileReader.password);
            connection = DriverManager.getConnection(p.getProperty("jst.url").toString(), p.getProperty("jst.username").toString(), p.getProperty("jst.password").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 释放数据库连接
     */
    public void closeConnection(ResultSet rs, PreparedStatement statement, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void closeConnection(PreparedStatement statement, Connection con) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 增加、删除、改
     * @param sql
     * @param params
     * @return
     * @throws java.sql.SQLException
     */
    public boolean updateByPreparedStatement(String sql, List<Object>params)throws SQLException{
        boolean flag = false;
        int result = -1;
        Connection connection = this.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int index = 1;
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;
        this.closeConnection(pstmt,connection);
        return flag;
    }

    /**
     * 查询单条记录
     * @param sql
     * @param params
     * @return
     * @throws java.sql.SQLException
     */
    public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{
        Map<String, Object> map = new HashMap<String, Object>();
        int index  = 1;
        Connection  connection = this.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();//返回查询结果
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();
        while(resultSet.next()){
            for(int i=0; i<col_len; i++ ){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
        }
        this.closeConnection(resultSet,pstmt,connection);
        return map;
    }

    /**查询多条记录
     * @param sql
     * @param params
     * @return
     * @throws java.sql.SQLException
     */
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        Connection connection = this.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            Map<String, Object> map = new HashMap<String, Object>();
            for(int i=0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        this.closeConnection(resultSet, pstmt, connection);
        return list;
    }

}

