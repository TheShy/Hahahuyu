package com.touyuanren.hahahuyu.audio;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.fragment.FragmentController;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by Administrator on 2017/7/13/013.
 */

public class MainAcitity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private LinearLayout llBottom;
    private RadioGroup mRadioGroup;

    private RadioButton rb_txt;
    private RadioButton rb_music;
    private RadioButton rb_video;
    private RadioButton rb_my;

    //当前选中的所在页面
    private int flag;

    private FragmentController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auddio_main_activity);
        setTitleLeftBtn();
        setLestTitle("教育");
        initView();
        initData();
    }

    private void initView() {
        llBottom = (LinearLayout) findViewById(R.id.llBottom);
        mRadioGroup = (RadioGroup) findViewById(R.id.hometab_radio);
        rb_txt = (RadioButton) findViewById(R.id.audio_main_rbtn_txt);
        rb_music = (RadioButton) findViewById(R.id.audio_main_rbtn_music);
        rb_video = (RadioButton) findViewById(R.id.audio_main_rbtn_video);
        rb_my = (RadioButton) findViewById(R.id.audio_main_rbtn_my);

        mRadioGroup.setOnCheckedChangeListener(this);
    }

    private void initData() {
        mController = FragmentController.getInstance(this, R.id.fl_content);
        mController.showFragment(0);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.audio_main_rbtn_txt:
                mController.showFragment(flag = 0);
                break;
            case R.id.audio_main_rbtn_music:
                mController.showFragment(flag = 1);
                break;
            case R.id.audio_main_rbtn_video:
                mController.showFragment(flag = 2);
                break;
            case R.id.audio_main_rbtn_my:
                mController.showFragment(flag = 3);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mController.onDestroy();
    }

    //处理节操


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
