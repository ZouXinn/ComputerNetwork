import 'package:flutter/material.dart';
import 'dart:async';

class ServicePage extends StatefulWidget{
  @override
  _ServiceState createState() => _ServiceState();
}

class _ServiceState extends State<ServicePage>{

  GlobalKey _formKey = new GlobalKey<FormState>();
  @override
  void initState(){
    super.initState();
  }

  @override
  Widget build(BuildContext context) {

    return Center(
      child: Form(
        key: _formKey,
        autovalidate: true,
        child: Column(
          children: <Widget>[
            TextFormField(
              autofocus: false,
              decoration: InputDecoration(
                labelText: "服务号",
                hintText: "请输入要请求的服务编号",
                prefixIcon: Icon(Icons.assignment_ind)
              ),
              validator: (input){
                //var val = int.parse(input);
                // bool right = true;
                // input.matches('[1-9][0-9]*');
                // for(int i=0;i<input.length;i++){
                //   if(input.)
                // }
                // try{
                //   int.parse(input);
                //   return null;
                // }catch(e){
                //   return "服务号必须为整数";
                // }
                //return val is int ? null : "服务号必须为整数";
                return input.trim().length >= 6 ? null : "用户名长度不小于6";
              },
            ),
            TextFormField(
              autofocus: false,
              decoration: InputDecoration(
                labelText: "参数",
                hintText: "请输入该服务需要的参数",
                prefixIcon: Icon(Icons.remove_red_eye)
              ),
              validator: (input){
                var val = int.parse(input);
                return val is int ? null : "参数必须为整数";
              },
              obscureText: true,
            ),
            RaisedButton(
              child: Text("请求服务"),
              onPressed: (){
                if((_formKey.currentState as FormState).validate()){// 输入满足条件则提交

                }
              },
            )
          ],
        ),
      )
    );
  }
  
}