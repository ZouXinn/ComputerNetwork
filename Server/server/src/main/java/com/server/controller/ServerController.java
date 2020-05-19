package com.server.controller;


import com.server.utils.jwt.JwtTool;
import com.server.utils.jwt.JwtVerifyResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.server.ignite.model.Response;
import com.server.ignite.IgniteHandler;
import com.server.services.Services;

@Controller
public class ServerController {

    @RequestMapping("/")
    @ResponseBody
    public Response hello(){
        Response rsp = new Response(true,"details","result");
        return rsp;
    }

    /*
        用户登录：会传入用户id和密码，需要进行验证，并返回是否登录成功、
        返回的应该是json数据,如果登录成功是否要把JWT附在其中返回？
     */
    @RequestMapping("/login")
    @ResponseBody
    public Response Login(String username,String pswd){
        Response response = new Response();
        if(IgniteHandler.checkPswd(username,pswd)) { // 如果账号密码正确
            response.status= true; // 表示登录成功
            response.details = "login success,you can find JWT in field \'result\'";
            response.result = JwtTool.createJwt(username);
            System.out.println("账号:"+username+",密码:"+pswd+" 登录成功!");
        }else {
            response.status= false;
            response.details = "login failed,username or password wrong";
            response.result = null;
            System.out.println("账号:"+username+",密码:"+pswd+" 登录失败!");
        }
        return response;
    }

    /*
        用户请求服务，传入的是json文件，
        如果用户有该服务的权限，则返回服务调用结果，否则拒绝服务
     */
    @RequestMapping("/service")
    @ResponseBody
    public Response requestService(String serviceId,String param,String jwt) {
        long sId = Long.valueOf(serviceId);
        long sParam = Long.valueOf(param);
        JwtVerifyResult result = JwtTool.verifyJwt(jwt);
        Response response = new Response();
        if(result.isValid()){ // jwt有效
            if(IgniteHandler.hasAuth(result.getUserId(),sId)){ // 可以请求服务
                String serviceResult = Services.getServiceOf(sId,sParam);
                response.status = true;
                response.details = "request served,result in field \'result\'";
                response.result = serviceResult;
                System.out.println("user:"+result.getUserId()+" called service "+serviceId+" successfully!");
            }else{
                response.status = false;
                response.details = "you have no authority to call this service";
                response.result = null;
                response.code = 2; // 没有权限
                System.out.println("user:"+result.getUserId()+" called service "+serviceId+",failed with code 2");
            }
        }else if(result.getInValidReason() == JwtVerifyResult.InValidReason.TimeOut){ // 超时
            response.status = false;
            response.details = "the jwt is invalid now,please login again";
            response.result = null;
            response.code = 1; // 登录超时
            System.out.println("user:"+result.getUserId()+" called service "+serviceId+",failed with code 1");
        }else{ // jwt损坏
            response.status = false;
            response.details = "the jwt is destroyed unexpectedly,please login again";
            response.result = null;
            response.code = 3; // jwt坏
            System.out.println("user:"+result.getUserId()+" called service "+serviceId+",failed with code 3");
        }
        return response;
    }
}
