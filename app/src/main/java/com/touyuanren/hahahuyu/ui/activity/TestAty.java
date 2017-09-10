package com.touyuanren.hahahuyu.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.touyuanren.hahahuyu.R;

/**
 * Created by Administrator on 2017/8/9/009.
 */

public class TestAty extends BaseActivity {

    private WebView webview;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lifo_activity);
        initView();
    }
    private void initView() {
        String url = "http://zi.dashixian.com";
        webview = (WebView) findViewById(R.id.webview);
        //启用支持JavaScript
        webview.getSettings().setJavaScriptEnabled(true);
        //启用支持DOM Storage
        webview.getSettings().setDomStorageEnabled(true);
        //加载web资源
        webview.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
    }
}
