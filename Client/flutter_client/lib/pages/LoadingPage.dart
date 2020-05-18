import 'package:flutter/material.dart';
import 'dart:async';


class LoadingPage extends StatefulWidget{
  @override
  _LoadingState createState() => _LoadingState();
}

class _LoadingState extends State<LoadingPage>{
  @override
  void initState(){
    super.initState();
    // 在加载页面停顿2s
    Future.delayed(Duration(seconds: 2),(){
      print("加载完成!");
      // 跳转到登录界面
      Navigator.of(context).pushReplacementNamed("login");
    });
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Center(
        child: Stack(
          children: <Widget>[
            // 加载页面的背景图片
            Image.asset("assets/images/loading.jpg"),
            Center(
              child: Text("loading...",
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 36.0,
                  decoration: TextDecoration.none,
                ),
              )
            ),
          ],
        )
      )
    );
  }
}