package com.touyuanren.hahahuyu.audio.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.bean.VedioInfo;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/18/018.
 */

public class VedioListDetail extends BaseActivity {

    private ArrayList<VedioInfo> mList = new ArrayList<>();
    WebView mWebView;
    private String id;
    private VedioInfo mVedioInfo=null;
    WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_list_details);
        setTitleLeftBtn();
        setTitleName("视频详情");

        init();
        initialized();
    }
    private void init() {
        mWebView = (WebView) findViewById(R.id.audio_list_wb);
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
        mVedioInfo = (VedioInfo) getIntent().getSerializableExtra("data");
        id = getIntent().getStringExtra("id");
        mWebView.loadUrl("http:www.hahahuyu.com" + mVedioInfo.getUrl());

    }

}
