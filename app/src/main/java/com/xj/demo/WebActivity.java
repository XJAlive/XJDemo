package com.xj.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        final WebView webView = findViewById(R.id.webview);
        WebSettings webSettings =  webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(this,"android");

        findViewById(R.id.tv_androidcalljs).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                webView.loadUrl("javascript:javacalljs()");

                                webView.loadUrl("javascript:javacalljswith(50)");

            }
        });

        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("javascript::javacalljswith(50)", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
        }
    }

    @JavascriptInterface
    public void jsCallAndroidArgs(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


}