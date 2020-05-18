import 'package:flutter/material.dart';
import 'dart:async';
import '../utils/Global.dart';

class LoginPage extends StatefulWidget{
  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<LoginPage>{

  GlobalKey _formKey = new GlobalKey<FormState>();
  @override
  void initState(){
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    if(Global.logined){
      return Center(
        child: Text("logined"),
      );
    }else{
      return Center(
        child: Form(
          key: _formKey,
          autovalidate: true,
          child: Column(
            children: <Widget>[
              TextFormField(
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
              RaisedButton(
                child: Text("登录"),
                onPressed: (){
                  //处理登录功能
                  if((_formKey.currentState as FormState).validate()){// 输入满足条件则提交

                  }else{ // 否则不提交

                  }
                },
              )
            ],
          ),
        )
      );
    }
  }
}