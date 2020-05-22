import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Global{
  // 记得将ip替换
  static const String url1 = "http://192.168.1.5:8080";
  //static const String url1 = "http://127.0.0.1:8080";
  static const String url2 = "http://192.168.1.5:8081";

  static bool logined = false;
  static String jwtStr = "";
  static String myId = "";

  static void logout(){
    logined = false;
    jwtStr = "";
    myId = "";
  }

  static void login(String id,String jwt){
    logined = true;
    myId = id;
    jwtStr = jwt;
  }

  static void showDlg(BuildContext context,String message){
    showDialog(
      context: context,
      builder: (ctx){
        return Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text(
                message,
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Colors.blue,
                  fontSize: 30,
                ),
              ),
              Center(
                child: RaisedButton(
                  child: Text(
                    "OK",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      color: Colors.blue,
                      fontSize: 30,
                    ),
                  ),
                  onPressed: (){
                    Navigator.of(context).pop(1);
                  },
                ),
              )
            ],
          ),
        );
      }
    );
  }
  static void showServiceDlg(BuildContext context,bool status,String detail,String string){
    String str;
    if(status){
      str = "结果为："+string;
    }else{
      str = string;
    }
    showDialog(
      context: context,
      builder: (ctx){
        return Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text(
                status?"服务请求成功":"服务请求失败",
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Colors.red,
                  fontSize: 50,
                ),
              ),
              Text(
                detail,
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Colors.red,
                  fontSize: 30,
                ),
              ),
              Text(
                str,
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Colors.red,
                  fontSize: 30,
                ),
              ),
              Center(
                child: RaisedButton(
                  child: Text(
                    "OK",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      color: Colors.blue,
                      fontSize: 30,
                    ),
                  ),
                  onPressed: (){
                    Navigator.of(context).pop(1);
                  },
                ),
              )
            ],
          ),
        );
      }
    );
  }
}