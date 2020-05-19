package com.server;

import com.server.ignite.IgniteHandler;
import com.server.utils.jwt.JwtTool;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import com.server.utils.jwt.*;

@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void connectDB(){
        //IgniteHandler.CreateTable();
        //1:首先获取Ignite对象
        //Ignite ignite = Ignition.start();
        // Register JDBC driver.
//        try{
//            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
//            Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
//            Statement stmt = conn.createStatement();



            // 创建用户的权限表
//            stmt.executeUpdate("CREATE TABLE User_Auth (" +
//                    " id VARCHAR NOT NULL, auth LONG NOT NULL, priority LONG" +
//                    " PRIMARY KEY (id, auth)) ");
//            stmt.executeUpdate("CREATE INDEX idx_id ON Users (id)");


//            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
//            Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
//            try (Statement stmt = conn.createStatement()) {
//                // Create table based on REPLICATED template.
//                stmt.executeUpdate("CREATE TABLE City (" +
//                        " id LONG PRIMARY KEY, name VARCHAR) " +
//                        " WITH \"template=replicated\"");
//                // Create table based on PARTITIONED template with one backup.
//                stmt.executeUpdate("CREATE TABLE Person (" +
//                        " id LONG, name VARCHAR, city_id LONG, " +
//                        " PRIMARY KEY (id, city_id)) " +
//                        " WITH \"backups=1, affinityKey=city_id\"");
//                // Create an index on the City table.
//                stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");
//                // Create an index on the Person table.
//                stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try {
            IgniteConfiguration cfg = new IgniteConfiguration();
            DataStorageConfiguration storageCfg = new DataStorageConfiguration();
            storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
            cfg.setDataStorageConfiguration(storageCfg);
            Ignite ignite = Ignition.start(cfg);

            //IgniteCache<Long, Long> personCache = ignite.cache("SQL_PUBLIC_PERSON");

            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
            Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
            Statement stmt = conn.createStatement();
            // Create table based on REPLICATED template.
            stmt.executeUpdate("CREATE TABLE City (" +
                    " id LONG PRIMARY KEY, name VARCHAR) " +
                    " WITH \"template=replicated\"");
            // Create table based on PARTITIONED template with one backup.
            stmt.executeUpdate("CREATE TABLE Person (" +
                    " id LONG, name VARCHAR, city_id LONG, " +
                    " PRIMARY KEY (id, city_id)) " +
                    " WITH \"backups=1, affinityKey=city_id\"");
            // Create an index on the City table.
            stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");
            // Create an index on the Person table.
            stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testJwt(){
        String id = "zhangsan";
        String token = JwtTool.createJwt(id);
        System.out.println(token);

        JwtVerifyResult result = JwtTool.verifyJwt(token);
        System.out.println("是否成功:"+result.isValid());
        System.out.println("失败原因:"+result.getInValidReason());
        System.out.println("用户ID:"+result.getUserId());
    }
}
