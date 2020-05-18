package com.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.server.ignite.model.Response;

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
    public String Login(){
        return "a";
    }

    /*
        用户请求服务，传入的是json文件，
        如果用户有该服务的权限，则返回服务调用结果，否则拒绝服务
     */
    @RequestMapping("/service")
    @ResponseBody
    public String requestService() {
        return "";
    }

}
