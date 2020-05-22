import 'package:flutter/material.dart';
import 'dart:async';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../utils/Global.dart';

class ServicePage extends StatefulWidget{
  @override
  _ServiceState createState() => _ServiceState();
}

class _ServiceState extends State<ServicePage>{
  GlobalKey _formKey = new GlobalKey<FormState>();
  TextEditingController _serviceIdController = new TextEditingController();
  TextEditingController _serviceParamController = new TextEditingController();
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
              controller: _serviceIdController,
              autofocus: false,
              decoration: InputDecoration(
                labelText: "服务号",
                hintText: "请输入要请求的服务编号",
                prefixIcon: Icon(Icons.assignment_ind)
              ),
              validator: (input){
                try{
                  int.parse(input);
                  return null;
                }catch(e){
                  return "服务号必须为整数";
                }
              },
            ),
            TextFormField(
              controller: _serviceParamController,
              autofocus: false,
              decoration: InputDecoration(
                labelText: "参数",
                hintText: "请输入该服务需要的参数",
                prefixIcon: Icon(Icons.remove_red_eye)
              ),
              validator: (input){
                try{
                  int.parse(input);
                  return null;
                }catch(e){
                  return "参数必须为整数";
                }
              },
            ),
            Flex(
                direction: Axis.horizontal,
                children: <Widget>[
                  Expanded(
                    flex: 1,
                    child: RaisedButton(
                      child: Text("请求站点1的服务"),
                      onPressed: () async{
                      //处理登录功能
                        if((_formKey.currentState as FormState).validate()){// 输入满足条件则提交
                          if(Global.logined){ // 已经登录
                            String reqUrl = Global.url1+"/service?serviceId=${_serviceIdController.text}&param=${_serviceParamController.text}&jwt=${Global.jwtStr}";
                            var res = await http.get(reqUrl);
                            String body = res.body;
                            var json = jsonDecode(body);
                            bool status = json['status'];
                            String detail = json['details'];
                            if(status){ // 成功
                              String result = json['result'];
                              Global.showServiceDlg(context, status, detail, result);
                            }else{
                              int code = json['code'];
                              String string = "";
                              switch(code){
                                case 1:
                                  string = "登录超时，请重新登录";
                                  setState(() {
                                    Global.logout();
                                  });
                                  break;
                                case 2:
                                  string = "您没有该服务的权限";
                                  break;
                                case 3:
                                  string = "JWT意外损坏，请重试";
                                  setState(() {
                                    Global.logout();
                                  });
                                  break;
                              }
                              Global.showServiceDlg(context, status, detail, string);
                            }
                          }else{ // 未登录
                            Global.showDlg(context, "请先登录！");
                          }
                        }
                      },
                    ),
                  ),
                  Expanded(
                    flex: 1,
                    child: RaisedButton(
                      child: Text("请求站点2的服务"),
                      onPressed: () async{
                      //处理登录功能
                        if((_formKey.currentState as FormState).validate()){// 输入满足条件则提交
                          if(Global.logined){ // 已经登录
                            String reqUrl = Global.url2+"/service?serviceId=${_serviceIdController.text}&param=${_serviceParamController.text}&jwt=${Global.jwtStr}";
                            var res = await http.get(reqUrl);
                            String body = res.body;
                            var json = jsonDecode(body);
                            bool status = json['status'];
                            String detail = json['details'];
                            if(status){ // 成功
                              String result = json['result'];
                              Global.showServiceDlg(context, status, detail, result);
                            }else{
                              int code = json['code'];
                              String string = "";
                              switch(code){
                                case 1:
                                  string = "登录超时，请重新登录";
                                  setState(() {
                                    Global.logout();
                                  });
                                  break;
                                case 2:
                                  string = "您没有该服务的权限";
                                  break;
                                case 3:
                                  string = "JWT意外损坏，请重新登录";
                                  setState(() {
                                    Global.logout();
                                  });
                                  break;
                              }
                              Global.showServiceDlg(context, status, detail, string);
                            }
                          }else{ // 未登录
                            Global.showDlg(context, "请先登录！");
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