package com.xj.demo;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
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
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(this, "android");

        findViewById(R.id.tv_androidcalljs).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:javacalljs()");
//                webView.loadUrl("javascript:javacalljswith(50)");

            }
        });

        findViewById(R.id.tv_androidcalljsargs).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("javascript:javacalljswith(50)",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                if (value != null && !value.isEmpty()) {
                                    Toast.makeText(WebActivity.this, "网页返回值：" + value,
                                        Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
                JsPromptResult result) {
//                return super.onJsPrompt(view, url, message, defaultValue, result);

                Toast.makeText(WebActivity.this, "native拦截了网页,jsPrompt：" + message,
                    Toast.LENGTH_SHORT).show();
                result.cancel();
                return true;

            }
        });

    }

    @JavascriptInterface
    public void jsCallAndroidArgs(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void jsCallAndroid() {
        Toast.makeText(this, "调用native无参方法", Toast.LENGTH_SHORT).show();
    }
}