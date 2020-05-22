package com.server.ignite.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class User {
    private static final AtomicLong ID_GEN = new AtomicLong();

    /*
    *   用户id
    * */
    private String id;

    /*
    *   用户密码
    * */
    private String password;

    public User(String id,String password) {
        this.id = id;
        this.password = password;
    }

    void setId(String newId){
        this.id = newId;
    }

    void setPswd(String newPassword){
        this.password = newPassword;
    }

    public String getId(){
        return this.id;
    }

    public String getPswd(){
        return this.password;
    }
}
