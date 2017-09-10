package com.touyuanren.hahahuyu.audio.activity;

import android.os.Bundle;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;

/**
 * Created by Administrator on 2017/8/7/007.
 */

public class SendAudioListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_news_list_activity);
        setTitleLeftBtn();
        setTitleName("我发布的音频");
    }
}
