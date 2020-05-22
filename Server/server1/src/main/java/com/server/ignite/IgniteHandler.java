package com.server.ignite;

import com.server.ignite.model.User;
import com.server.ignite.model.UserAuthKey;
import com.server.ignite.model.User_Auth;
import com.server.ignite.store.UserAuthStore;
import com.server.ignite.store.UserStore;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import javax.cache.configuration.FactoryBuilder;
import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IgniteHandler {
    private static Ignite ignite = null;
    private static IgniteCache<String, User> userCache = null;
    private static IgniteCache<UserAuthKey, User_Auth> authCache = null;

    public static void Init(){
        IgniteConfiguration cfg = new IgniteConfiguration();
        //userCacheCfg
        CacheConfiguration<String, User> userCacheCfg = new CacheConfiguration<String, User>();
        userCacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(UserStore.class));
        userCacheCfg.setReadThrough(true);
        userCacheCfg.setWriteThrough(true);
        userCacheCfg.setName("usersCache");
        //authCacheCfg
        CacheConfiguration<UserAuthKey, User_Auth> authCacheCfg = new CacheConfiguration<>();
        authCacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(UserAuthStore.class));
        authCacheCfg.setReadThrough(true);
        authCacheCfg.setWriteThrough(true);
        authCacheCfg.setName("userAuthCache");
        //set cacheCfg
        cfg.setCacheConfiguration(userCacheCfg,authCacheCfg);
        //start ignite
        ignite = Ignition.start(cfg);
        //get userCache
        userCache = ignite.getOrCreateCache("usersCache");
        userCache.loadCache(null);
        //get authCache
        authCache = ignite.getOrCreateCache("userAuthCache");
        authCache.loadCache(null);
    }



    public static void start(){
        // Apache Ignite node configuration.
        IgniteConfiguration cfg = new IgniteConfiguration();

        // Ignite persistence configuration.
        DataStorageConfiguration storageCfg = new DataStorageConfiguration();

        // Enabling the persistence.
        storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);

        // Applying settings.
        cfg.setDataStorageConfiguration(storageCfg);

        ignite = Ignition.start(cfg);

    }
    public static void CreateTable() {

    }

    // 传入账号密码，验证用户账号密码是否正确
    public static boolean checkPswd(String id,String pswd) {
        User user = userCache.get(id);
        if(user != null && user.getPswd().equals(pswd)){
            return true;
        }
        return false;
    }

    // 验证用户是否有使用服务service的权限
    public static boolean hasAuth(String id,long service){
        UserAuthKey key = new UserAuthKey(id,service);
        User_Auth user_auth = authCache.get(key);
        if(user_auth != null && user_auth.getSite() == 2){
            return true;
        }
        return false;
    }

}
