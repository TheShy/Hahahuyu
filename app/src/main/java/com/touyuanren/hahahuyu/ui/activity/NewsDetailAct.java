package com.touyuanren.hahahuyu.ui.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.touyuanren.hahahuyu.R;


/**
 * Created by Jing on 2016/9/20.
 * 新闻详情
 */
public class NewsDetailAct extends BaseActivity {
    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news_detail);
        setTitleLeftBtn();
        mWebview = (WebView) findViewById(R.id.wv_news_detail);
        WebSettings webSetting = mWebview.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setLoadWithOverviewMode(true);
        final String contnetId = getIntent().getStringExtra("content_id");
        showLoading();
        mWebview.loadUrl("http://oms.hahahuyu.com/hd/Android/content?contentId=" + contnetId);

        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    hideLoading();
                }
            }


        });
    }
}
