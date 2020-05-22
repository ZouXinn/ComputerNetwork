package com.server.ignite.store;

import com.server.ignite.model.User;
import com.server.ignite.model.UserAuthKey;
import com.server.ignite.model.User_Auth;
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

public class UserAuthStore implements CacheStore<UserAuthKey, User_Auth> {
    static final String url = "jdbc:mysql://127.0.0.1/computer-network?serverTimezone=UTC";
    static final String username = "root";
    static final String password = "root";

    @Override
    public void loadCache(IgniteBiInClosure<UserAuthKey, User_Auth> clo, @Nullable Object... args) throws CacheLoaderException {
        System.out.println(">> Loading cache from store...");

        try {
            Connection conn = connection();
            PreparedStatement st = conn.prepareStatement("select * from user_auth");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1)+"----"+rs.getLong(2)+"----"+rs.getInt(3));

                User_Auth user_auth = new User_Auth(rs.getString(1),rs.getLong(2),rs.getInt(3));
                clo.apply(user_auth.getKey(),user_auth);
            }

        } catch (Exception e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }

    @Override
    public void sessionEnd(boolean commit) throws CacheWriterException {

    }

    @Override
    public User_Auth load(UserAuthKey key) throws CacheLoaderException {
        System.out.println(">> Loading usersAuth from store...");
        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from user_auth where id = ? AND sId = ?")) {
                st.setString(1, key.id);
                st.setLong(2, key.sId);
                ResultSet rs = st.executeQuery();
                return rs.next() ? new User_Auth(rs.getString(1),rs.getLong(2),rs.getInt(3)) : null;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }

    @Override
    public Map<UserAuthKey, User_Auth> loadAll(Iterable<? extends UserAuthKey> keys) throws CacheLoaderException {
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends UserAuthKey, ? extends User_Auth> entry) throws CacheWriterException {
        System.out.println(">> writing userAuth to store...");
        try (Connection conn = connection()) {
            PreparedStatement st = conn.prepareStatement("insert into user_auth(id,sId,site) VALUES(?,?,?) ");
            st.setString(1,entry.getValue().getId());
            st.setLong(2,entry.getValue().getsId());
            st.setInt(3,entry.getValue().getSite());
            st.executeUpdate();
            System.out.println(">> add auth to store...");
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to add auth to table store.", e);
        }
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends UserAuthKey, ? extends User_Auth>> entries) throws CacheWriterException {

    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement("delete from user_auth where id = ? AND sId = ?")) {
                st.setString(1, ((UserAuthKey)key).id);
                st.setLong(2,((UserAuthKey)key).sId);
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

    private Connection connection() throws SQLException {
        // Open connection to your RDBMS systems (Oracle, MySQL, Postgres, DB2, Microsoft SQL, etc.)
        // In this example we use H2 Database for simplification.
        Connection conn = DriverManager.getConnection(url,username,password);
        conn.setAutoCommit(true);
        return conn;
    }
}
