package com.server.ignite.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class User {
    private static final AtomicLong ID_GEN = new AtomicLong();

    /*
    *   用户id，加索引
    * */
    @QuerySqlField(index = true)
    public String id;

    /*
    *   用户密码，不加索引
    * */
    @QuerySqlField
    public String pswd;

    /*
    *   用户权限的list，不加索引
    * */
    @QuerySqlField
    public List<Integer> authority;

    public User(String id,String pswd,List<Integer> authority) {
        this.id = id;
        this.pswd = pswd;
        this.authority = authority;
    }



}
