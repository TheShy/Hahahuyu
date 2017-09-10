package com.touyuanren.hahahuyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.touyuanren.hahahuyu.R;


/**
 * Created by Jing on 2016/9/6.
 * 活动更多详情
 */
public class MoreMsgAct extends BaseActivity {
    private WebView mWebView;
    /**
     * 更多详情的url
     */
    String detailMsg;

    private ProgressBar pg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_more_msg);
        setTitleLeftBtn();
        setTitleName("活动详情");
        Intent mIntent = getIntent();
        detailMsg = mIntent.getStringExtra("moremsg");
        mWebView = (WebView) findViewById(R.id.wv_more_msg_act);
        init();
//        加载webview数据；
        WebSettings webSetting = mWebView.getSettings();
//        webSetting.setJavaScriptEnabled(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (null != detailMsg) {
            Log.e("detailMsg", detailMsg);
            mWebView.loadUrl(detailMsg);
        }
    }


    private void init() {
        // TODO 自动生成的方法存根
        pg1=(ProgressBar) findViewById(R.id.progressBar1);

        mWebView.setWebViewClient(new WebViewClient(){
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 自动生成的方法存根
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings seting=mWebView.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if(newProgress==100){
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
                }

            }
        });
    }
}
