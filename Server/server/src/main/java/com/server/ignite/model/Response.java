package com.server.ignite.model;

public class Response {
    public boolean status; // 请求是否成功
    public String details; // 显示细节
    public String result; // 显示请求结果

    public Response(boolean status,String details,String result){
        this.status = status;
        this.details = details;
        this.result = result;
    }
}
