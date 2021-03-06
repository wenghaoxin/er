package com.everywhere.trip.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.everywhere.trip.base.Constants;
import com.just.agentweb.AgentWeb;

public class AndroidInterface {
    private AgentWeb agentWeb;
    Context context;

    public AndroidInterface(AgentWeb agentWeb, Context context) {
        this.agentWeb = agentWeb;
        this.context = context;
    }
    @JavascriptInterface
    public  void callAndroid(String type, int id){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.DATA, id);
        context.startActivity(intent);
    }
    @JavascriptInterface
    public void callAndroid(String type) {
//        callAndroid('route_details', id)
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
