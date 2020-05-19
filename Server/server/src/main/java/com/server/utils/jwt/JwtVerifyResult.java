package com.server.utils.jwt;

// jwt的验证结果
public class JwtVerifyResult {
    public enum InValidReason{ // 如果无效，无效的原因
        TimeOut, // 超时
        Destroyed, // JWT已损坏
        None, // 无
    };
    boolean valid; // 是否有效
    InValidReason reason = InValidReason.None;
    String userId; // 用户id
    public JwtVerifyResult(boolean valid,String userId){
        this.valid = valid;
        this.userId = userId;
    }
    public JwtVerifyResult(boolean valid,String userId,InValidReason reason){
        this(valid,userId);
        this.reason = reason;
    }
    public boolean isValid(){
        return this.valid;
    }
    public InValidReason getInValidReason(){
        return this.reason;
    }
    public String getUserId(){
        return this.userId;
    }
}