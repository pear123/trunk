package com.arvato.cc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-26
 * Time: 下午9:33
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesFileReader {
    public static String driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/bsh";
    public static String username = "root";
    public static String password = "admin";
    public static String webUrl = "http://localhost:8080/interfaces/services/api/test";

//    public static String url = "jdbc:mysql://rds1pu5i1ksx73kv8o1l.mysql.rds.aliyuncs.com:3306/sys_info";
//    public static String username = "jusrqiteijbx";
//    public static String password = "RobinChris235";
//    public static String webUrl = "http://121.41.177.193:80/interfaces/services/api/test";
//    static{
//        try {
//            //加载dbconfig.properties配置文件
//            Properties prop = new Properties();
//            prop.load(PropertiesFileReader.class.getResourceAsStream("config/jstDB.properties"));
//            driver = prop.getProperty("jdbc.driverClassName");
//            url = prop.getProperty("jdbc.url");
//            username = prop.getProperty("jdbc.username");
//            password = prop.getProperty("jdbc.password");
//            webUrl = prop.getProperty("JST.WEBSERVICE");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public static final String driver = prop.getProperty("jdbc.driverClassName");
//    public static final String url = prop.getProperty("jdbc.url");
//    public static final String username = prop.getProperty("jdbc.username");
//    public static final String password = prop.getProperty("jdbc.password");
//    public static final String webUrl = prop.getProperty("JST.WEBSERVICE");


}
