package com.server.ignite.model;

public class Response {
    public boolean status; // 请求是否成功
    public String details; // 显示细节
    public String result; // 显示请求结果
    public int code = 0;// 0 正常，1登录超时，2没有权限,3 jwt损坏

    public Response() {

    }

    public Response(boolean status, String details, String result){
        this.status = status;
        this.details = details;
        this.result = result;
    }
}
