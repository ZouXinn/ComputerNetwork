package com.server.ignite.model;

public class User_Auth {
    private String id;
    private long sId;
    private int site;

    public User_Auth(String id,long sId,int site){
        this.id = id;
        this.sId = sId;
        this.site = site;
    }

    public String getId(){
        return this.id;
    }

    public long getsId(){
        return this.sId;
    }

    public int getSite(){
        return this.site;
    }

    public UserAuthKey getKey(){
        return new UserAuthKey(id,sId);
    }
}
