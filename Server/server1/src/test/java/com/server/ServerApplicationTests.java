package com.server;

import com.server.ignite.IgniteHandler;
import com.server.ignite.model.UserAuthKey;
import com.server.ignite.model.User_Auth;
import com.server.ignite.store.UserAuthStore;
import com.server.ignite.store.UserStore;
import com.server.utils.jwt.JwtTool;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.util.Iterator;
import java.util.List;
import com.server.ignite.model.User;

import com.server.utils.jwt.*;

import javax.cache.configuration.FactoryBuilder;

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
    void testPersistence(){
        Connection conn = null;
        try {
            // Register JDBC driver
            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
            // Open JDBC connection
            conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
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
        // Populate City table
        try (PreparedStatement stmt =
                     conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)")) {
            stmt.setLong(1, 1L);
            stmt.setString(2, "Forest Hill");
            stmt.executeUpdate();
            stmt.setLong(1, 2L);
            stmt.setString(2, "Denver");
            stmt.executeUpdate();
            stmt.setLong(1, 3L);
            stmt.setString(2, "St. Petersburg");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Populate Person table
        try (PreparedStatement stmt =
                     conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)")) {
            stmt.setLong(1, 1L);
            stmt.setString(2, "John Doe");
            stmt.setLong(3, 3L);
            stmt.executeUpdate();
            stmt.setLong(1, 2L);
            stmt.setString(2, "Jane Roe");
            stmt.setLong(3, 2L);
            stmt.executeUpdate();
            stmt.setLong(1, 3L);
            stmt.setString(2, "Mary Major");
            stmt.setLong(3, 1L);
            stmt.executeUpdate();
            stmt.setLong(1, 4L);
            stmt.setString(2, "Richard Miles");
            stmt.setLong(3, 2L);
            stmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testStartIgnite(){
        // Connecting to the cluster.
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        Ignite ignite = Ignition.start("F:\\ComputerNetwork\\Server\\server\\src\\main\\java\\com\\server\\xmls\\ignite-cfg.xml");
        IgniteCache<String, User> cache = ignite.getOrCreateCache("usersCache");

        cache.loadCache(null);
        QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery("select id, password from User"));
        System.out.println(cursor.getAll());

    }
    @Test
    void testStart(){
        //Ignition.setClientMode(true);
        Ignite ignite = Ignition.start();
    }

    @Test
    void testUser(){

        IgniteConfiguration cfg = new IgniteConfiguration();
        CacheConfiguration<String, User> cacheCfg = new CacheConfiguration<>();
        cacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(UserStore.class));
        cacheCfg.setReadThrough(true);
        cacheCfg.setWriteThrough(true);
        cacheCfg.setName("usersCache");
        //cacheCfg.

        cfg.setCacheConfiguration(cacheCfg);
        // Start Ignite node.
        Ignite ignite = Ignition.start(cfg);

        IgniteCache<String, User> cache = ignite.getOrCreateCache("usersCache");

        cache.loadCache(null);
        User user = cache.get("zhangsan");
        //QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery("select id, password from Users"));
        //System.out.println(cursor.getAll());
        System.out.println("id = "+user.getId()+" , pwd = "+user.getPswd());
    }

    @Test
    void testAuth(){
        IgniteConfiguration cfg = new IgniteConfiguration();
        CacheConfiguration<UserAuthKey, User_Auth> cacheCfg = new CacheConfiguration<>();
        cacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(UserAuthStore.class));
        cacheCfg.setReadThrough(true);
        cacheCfg.setWriteThrough(true);
        cacheCfg.setName("userAuthCache");
        //cacheCfg.

        cfg.setCacheConfiguration(cacheCfg);
        // Start Ignite node.
        Ignite ignite = Ignition.start(cfg);

        IgniteCache<UserAuthKey, User_Auth> cache = ignite.getOrCreateCache("userAuthCache");

        cache.loadCache(null);
        UserAuthKey key = new UserAuthKey("lisi11",3L);
        User_Auth user_auth = cache.get(key);
        //QueryCursor<List<?>> cursor = cache.query(new SqlFieldsQuery("select id, password from Users"));
        //System.out.println(cursor.getAll());
        if(user_auth == null){
            System.out.println("get null");
        }else {
            System.out.println("id = "+user_auth.getId()+" , sId = "+user_auth.getsId()+" , site = "+user_auth.getSite());
        }

    }

    @Test
    void testInitIgniteHandler(){
        IgniteHandler.Init();
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
