import 'package:flutter/material.dart';
import 'dart:async';
import '../utils/Global.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class LoginPage extends StatefulWidget{
  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<LoginPage>{

  GlobalKey _formKey = new GlobalKey<FormState>();
  TextEditingController _idController = new TextEditingController();
  TextEditingController _pswdController = new TextEditingController();
  @override
  void initState(){
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    if(Global.logined){
      return Center(
        child: Column(
          children: <Widget>[
            Text(
              "已登录",
              style: TextStyle(
                fontSize: 20.0,
                color: Colors.blue,
              ),
            ),
            Text(
              Global.myId,
              style: TextStyle(
                fontSize: 30.0,
                color: Colors.red
              ),
            ),
            RaisedButton(
              child: Text("登出"),
              onPressed: (){
                setState(() {
                  Global.logout();
                });
              },
            )
          ],
        )
      );
    }else{
      return Center(
        child: Form(
          key: _formKey,
          autovalidate: true,
          child: Column(
            children: <Widget>[
              TextFormField(
                controller: _idController,
                autofocus: false,
                decoration: InputDecoration(
                  labelText: "用户名",
                  hintText: "请输入用户名",
                  prefixIcon: Icon(Icons.assignment_ind)
                ),
                validator: (input){
                  return input.trim().length >= 6 ? null : "用户名长度不小于6";
                },
              ),
              TextFormField(
                controller: _pswdController,
                autofocus: false,
                decoration: InputDecoration(
                  labelText: "密码",
                  hintText: "请输入密码",
                  prefixIcon: Icon(Icons.remove_red_eye)
                ),
                validator: (input){
                  return input.length >= 6 ? null : "密码长度不小于6";
                },
                obscureText: true,
              ),
              Flex(
                direction: Axis.horizontal,
                children: <Widget>[
                  Expanded(
                    flex: 1,
                    child: RaisedButton(
                      child: Text("登录站点1"),
                      onPressed: () async{
                        //处理登录功能
                        if((_formKey.currentState as FormState).validate()){// 输入满足条件则提交
                          String reqUrl = Global.url1+"/login?username=${_idController.text}&pswd=${_pswdController.text}";
                          var res = await http.get(reqUrl);
                          String body = res.body;
                          var json = jsonDecode(body);
                          bool status = json['status'];
                          if(status){ // 登录成功
                            setState(() {
                              Global.login(_idController.text, json['result']);
                            });
                            print(Global.jwtStr);
                          }else{ // 登陆失败 -- 弹出弹窗，表明登录失败
                            Global.showDlg(context, "登录失败，账号或密码错误!");
                          }
                        }
                      },
                    ),
                  ),
                  Expanded(
                    flex: 1,
                    child: RaisedButton(
                      child: Text("登录站点2"),
                      onPressed: ()async{
                        //处理登录功能
                        if((_formKey.currentState as FormState).validate()){// 输入满足条件则提交
                          String reqUrl = Global.url2+"/login?username=${_idController.text}&pswd=${_pswdController.text}";
                          var res = await http.get(reqUrl);
                          String body = res.body;
                          var json = jsonDecode(body);
                          bool status = json['status'];
                          if(status){ // 登录成功
                            setState(() {
                              Global.login(_idController.text, json['result']);
                            });
                            print(Global.jwtStr);
                          }else{ // 登陆失败 -- 弹出弹窗，表明登录失败
                            Global.showDlg(context, "登录失败，账号或密码错误!");
                          }
                        }
                      },
                    ),
                  ),
                ],
              )
            ],
          ),
        )
      );
    }
  }
}