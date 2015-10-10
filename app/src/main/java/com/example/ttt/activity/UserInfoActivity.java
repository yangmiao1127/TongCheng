package com.example.ttt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ttt.R;

/**
 * Created by 1 on 2015/10/5.
 */
public class UserInfoActivity extends Activity{
    private String userId;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        Intent intent=getIntent();
        userId=intent.getStringExtra("userImage");
        setTitle("查看详情");


        String website="http://www.douban.com/location/people/"+userId+"/";

        webView= (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(website);


    }
}
