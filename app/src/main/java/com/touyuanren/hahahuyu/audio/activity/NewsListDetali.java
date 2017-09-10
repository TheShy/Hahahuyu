package com.touyuanren.hahahuyu.audio.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.bean.NewsInfo;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/14/014.
 */

public class NewsListDetali extends BaseActivity {


    private ArrayList<NewsInfo.DataBean.InfoBean> mList = new ArrayList<>();
    WebView mWebView;
    private NewsInfo.DataBean.InfoBean mInfoBean=null;
    WebSettings mWebSettings;


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_activity);
        setTitleLeftBtn();

        setTitleName("文章详情");

        init();
        initialized();
    }

    private void init() {
        mWebView = (WebView) findViewById(R.id.news_wb);
        mWebSettings = mWebView.getSettings();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setSupportZoom(true);
    }

    private void initialized() {
        mList = new ArrayList<>();
        mInfoBean = (NewsInfo.DataBean.InfoBean) getIntent().getSerializableExtra("info");
//        id = getIntent().getStringExtra("id");

        mWebView.loadUrl("http:www.hahahuyu.com" + mInfoBean.getH5_url());

    }




}
