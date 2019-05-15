package com.example.day_androisus;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Android与js交互
     */
    private Button mBtJs;
    private WebView mWeb;
    private String mMsg = "今天又是一天";
    /**
     * Android调用js方法2_evaluatejavascript
     */
    private Button mBtJs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtJs = (Button) findViewById(R.id.bt_js);
        mBtJs.setOnClickListener(this);
        mWeb = (WebView) findViewById(R.id.web);


        WebSettings settings = mWeb.getSettings();
        //支持js交互
        settings.setJavaScriptEnabled(true);
        //file:///android_asset/固定的
        mWeb.loadUrl("file:///android_asset/text.html");
        mBtJs2 = (Button) findViewById(R.id.bt_js2);
        mBtJs2.setOnClickListener(this);

        //js调用Android方法1
        //参数1:桥梁的类
        //参数2:桥梁类的方法名
        mWeb.addJavascriptInterface(new AndroidJs(),"android");




        //js调Android2
        mWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               //根据协议的参数，判断是否是所需要的url
                //一般根据scheme(协议格式)&authority(协议名)判断(前两个参数)
                //假设传入进来 url="js://webview?arg1=111&arg2=222"(同时也是约定好的需要拦截)
                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        System.out.println("js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        Set<String> collection = uri.getQueryParameterNames();
                        Iterator<String> iterator = collection.iterator();
                        while (iterator.hasNext()) {
                            String next = iterator.next();
                            String value = uri.getQueryParameter(next);
                            System.out.println("key:" + next + ",value:" + value);
                        }
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_js:
                //注意调用js方法名要对应上
                //webview调用javascript的calljs()方法
                mWeb.loadUrl("javascript:callJs('" + mMsg + "')");
                break;
            case R.id.bt_js2:
                mWeb.evaluateJavascript("javascript:callJs('" + mMsg + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        //接收返回值用
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
