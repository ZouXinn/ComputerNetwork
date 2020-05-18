import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'pages/LoadingPage.dart';
import 'pages/LoginPage.dart';
import 'pages/ServicePage.dart';
import 'utils/Global.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget{
  static const String _title = "My Client";

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: _title,
      home: MyHomePage()
    );
  }
}

class MyHomePage extends StatefulWidget{
  @override
  _MyHomePage createState() => _MyHomePage();
}

class _MyHomePage extends State<MyHomePage>{
  int currentIndex = 1;
  List<StatefulWidget> pageList = [
    LoginPage(),
    ServicePage()
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("My Client"),
      ),
      body: this.pageList[this.currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            title: Text("User"),
            icon: Icon(Icons.assignment_ind)
          ),
          BottomNavigationBarItem(
            title: Text("Services"),
            icon: Icon(Icons.business_center)
          ),
        ],
        currentIndex: this.currentIndex,
        onTap: (index){
          setState(() {
            this.currentIndex = index;
          });
        },
      ),
    );
  }
}