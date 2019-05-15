package com.example.day_androisus;

import android.webkit.JavascriptInterface;

public class AndroidJs extends Object {
    //定义一个与js对象映射关系的Android类 继承object
    //定义js调用的方法 被js调用需要加@JavascriptInterface，防止被攻击
    @JavascriptInterface
    public  void CallAndroid(String msg){
        System.out.print(msg);
    }
}
