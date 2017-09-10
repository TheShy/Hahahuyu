package com.touyuanren.hahahuyu.audio.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.bean.AudioInfo;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/27/027.
 */

public class AudioListDetail extends BaseActivity {

    //
//    @BindView(R.id.audio_detail_tv_title)
//    TextView title;
//
//    @BindView(R.id.audio_detali_iv)
//    ImageView img;
//    @BindView(R.id.audio_detali_tv_send_name)
//    TextView send_name;
//    @BindView(R.id.btn_start)
//    Button btnStart;
//    @BindView(R.id.tv_progress)
//    TextView tvProgress;
//    @BindView(R.id.seekbar)
//    SeekBar seekbar;
//    @BindView(R.id.tv_size)
//    TextView tvSize;
    private ArrayList<AudioInfo> mList = new ArrayList<>();
    WebView mWebView;
    private String id;
    private AudioInfo mAudioInfo = null;
    WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_list_details);
        setTitleLeftBtn();
        setTitleName("音频详情");

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
        mAudioInfo = (AudioInfo) getIntent().getSerializableExtra("data");
        id = getIntent().getStringExtra("id");

        mWebView.loadUrl("http:www.hahahuyu.com" + mAudioInfo.getUrl());

    }


}



