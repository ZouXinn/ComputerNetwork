package com.server.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IgniteHandler {
    private static Connection conn;
//    static {
//        Ignite ignite = Ignition.start();
//        try {
//            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
//            conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public IgniteHandler(){
        // Register JDBC driver.
        //Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

    }

    public static void CreateTable() {
        // 创建表.
        try (Statement stmt = conn.createStatement()) {
            // 创建用户表，有用户的账号和密码
            stmt.executeUpdate("CREATE TABLE Users (" +
                    " id VARCHAR PRIMARY KEY, password VARCHAR) ");
            // 创建用户的权限表
            stmt.executeUpdate("CREATE TABLE User_Auth (" +
                    " id VARCHAR , auth LONG , priority LONG" +
                    " PRIMARY KEY (id, auth)) ");
            // 创建User表的索引
            stmt.executeUpdate("CREATE INDEX user_id_index ON Users (id)");
            // 创建User_Auth表的索引
            stmt.executeUpdate("CREATE INDEX user_id_index ON User_Auth (id)");
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }

    // 传入账号密码，验证用户账号密码是否正确
    public boolean checkPswd(String id,String pswd) {



        return true;
    }

    // 验证用户是否有使用服务service的权限
    public boolean hasAuth(String id,String service){
        ArrayList<String> list = getAuthServiceList(id);
        return list.contains(service);
    }

    // 获取用户有权限使用的服务的列表
    public ArrayList<String> getAuthServiceList(String id){




        ArrayList<String> list = new ArrayList<String>();
        return list;
    }
}
