package com.youe.cd.apitest.dao;

import com.youe.cd.apitest.util.Config;
import com.youe.cd.apitest.util.DateUtil;
import org.testng.annotations.Test;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MysqlDao {
    //Map<String, String> dbConnMap = getDBConnMap();

    public static Map<String, String> getDBConnMap() {
        Map<String, String> dbConnMap = new HashMap<String, String>(); //重要：使用这种方式初初化

        dbConnMap.put("dbDriver", Config.DB_DRIVER);
        dbConnMap.put("dbUrl", Config.DB_URL);
        dbConnMap.put("dbUserName", Config.DB_USERNAME);
        dbConnMap.put("dbPassword", Config.DB_PASSWORD);

        return dbConnMap;
    }


    //@Test
    public static void GetConnection(Map<String, String> dbConnMap) {
        try {
            Class.forName(dbConnMap.get("dbDriver"));  //注册jdbc驱动
            System.out.println("成功加载mysql驱动");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到mysql驱动");
            e.printStackTrace();
        }

        String url = dbConnMap.get("dbUrl");
        Connection conn;

        try {
            conn = DriverManager.getConnection(url, dbConnMap.get("dbUserName"), dbConnMap.get("dbPassword"));
            Statement stmt = conn.createStatement();  //创建statement对象
            System.out.println("成功连接到数据库");
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public static ResultSet queryTable(Map<String, String> dbConnMap, String sql) {
        ResultSet rs = null;

        try {
            Class.forName(dbConnMap.get("dbDriver"));  //注册jdbc驱动
            System.out.println("成功加载mysql驱动");

            String url = dbConnMap.get("dbUrl");
            Connection conn;

            conn = DriverManager.getConnection(url, dbConnMap.get("dbUserName"), dbConnMap.get("dbPassword"));
            Statement stmt = conn.createStatement();  //创建statement对象
            System.out.println("成功连接到数据库");

            //String sql = "select id,classify,create_time from t_classify where id = 56";
            rs = stmt.executeQuery(sql);

            System.out.println("id" + "\t" + "classify" + "\t" + "create_time");
            while(rs.next()) {
                System.out.print(rs.getInt("id") + "\t");
                System.out.print(String.format("%-8s\t", rs.getString("classify"))); //-表示左对齐，8表示占8个字符
                System.out.println(rs.getDate("create_time") + "\t");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    public static void updateTable(Map<String, String> dbConnMap, String sql) {
        //ResultSet rs = null;

        try {
            Class.forName(dbConnMap.get("dbDriver"));  //注册jdbc驱动
            System.out.println("成功加载mysql驱动");

            String url = dbConnMap.get("dbUrl");
            Connection conn;

            conn = DriverManager.getConnection(url, dbConnMap.get("dbUserName"), dbConnMap.get("dbPassword"));
            Statement stmt = conn.createStatement();  //创建statement对象
            System.out.println("成功连接到数据库");

            //String sql = "select id,classify,create_time from t_classify where id = 56";
            stmt.executeUpdate(sql);

            /*System.out.println("id" + "\t" + "classify" + "\t" + "create_time");
            while(rs.next()) {
                System.out.print(rs.getInt("id") + "\t");
                System.out.print(String.format("%-8s\t", rs.getString("classify"))); //-表示左齐，8表示占8个字符
                System.out.println(rs.getDate("create_time") + "\t");
            }

            rs.close();*/
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //return rs;
    }

    @Test
    public void runTest() {
        String sql = "select id,classify,create_time from t_classify";
        String sql2 = "update t_classify set classify = '教育大数据ch2' where id = 56";
        String sql3 = "insert into t_classify(id, classify, create_time) values(62, '科技', '2017-11-30')";
        String sql4 = "delete from t_classify where id = 62";

        //DateUtil.getDate();

        GetConnection(Config.dbConnMap);
        updateTable(Config.dbConnMap, sql2);
        //updateTable(Config.dbConnMap, sql3);
        //updateTable(Config.dbConnMap, sql4);
        queryTable(Config.dbConnMap, sql);
    }



}
