package com.server.ignite.store;

import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.SpringResource;
import org.jetbrains.annotations.Nullable;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.Map;
import com.server.ignite.model.User;

public class UserStore implements CacheStore<String,User> {
    //@SpringResource(resourceName = "igniteDataSource")
    //private DataSource dataSource;

    static final String url = "jdbc:mysql://127.0.0.1/computer-network?serverTimezone=UTC";
    static final String username = "root";
    static final String password = "root";

    @Override
    public void loadCache(IgniteBiInClosure<String, User> clo, @Nullable Object... args) throws CacheLoaderException {
        System.out.println(">> Loading cache from store...");
        try {
            Connection conn = connection();
            PreparedStatement st = conn.prepareStatement("select * from Users");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1)+"------------"+rs.getString(2));

                User user = new User(rs.getString(1),  rs.getString(2));
                clo.apply(user.getId(), user);
            }
        } catch (Exception e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }

    @Override
    public void sessionEnd(boolean commit) throws CacheWriterException {

    }

    @Override
    public User load(String key) throws CacheLoaderException {
        System.out.println(">> Loading users from store...");
        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from Users where id = ?")) {
                st.setString(1, key);
                ResultSet rs = st.executeQuery();
                return rs.next() ? new User(rs.getString(1),  rs.getString(2)) : null;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }

    @Override
    public Map<String, User> loadAll(Iterable<? extends String> keys) throws CacheLoaderException {
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends User> entry) throws CacheWriterException {
        System.out.println(">> writing user to store...");
        try (Connection conn = connection()) {
            PreparedStatement st = conn.prepareStatement("insert into Users(id,password) VALUES(?,?) ");
            st.setString(1,entry.getValue().getId());
            st.setString(2,entry.getValue().getPswd());
            st.executeUpdate();
            System.out.println(">> add user to store...");
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to add user to table store.", e);
        }
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends String, ? extends User>> entries) throws CacheWriterException {

    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement("delete from Users where id=?")) {
                st.setString(1, (String)key);
                st.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new CacheWriterException("Failed to delete: " + key, e);
        }
    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {

    }

    private Connection connection() throws SQLException  {
        // Open connection to your RDBMS systems (Oracle, MySQL, Postgres, DB2, Microsoft SQL, etc.)
        // In this example we use H2 Database for simplification.
        Connection conn = DriverManager.getConnection(url,username,password);
        conn.setAutoCommit(true);
        return conn;
    }
}
