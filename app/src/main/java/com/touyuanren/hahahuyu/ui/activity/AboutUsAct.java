package com.touyuanren.hahahuyu.ui.activity;

import android.os.Bundle;

import com.touyuanren.hahahuyu.R;


/**
 * Created by Jing on 2016/10/26.
 */
public class AboutUsAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_about_us);
        setTitleLeftBtn();
        setTitleName(R.string.about_us);
    }
}
