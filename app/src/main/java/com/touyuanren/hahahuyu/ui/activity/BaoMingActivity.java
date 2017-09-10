package com.touyuanren.hahahuyu.ui.activity;

import android.os.Bundle;

import com.touyuanren.hahahuyu.R;


/**
 * Created by Jing on 2016/2/3.或者写成不同的activity
 * 根据传入的比赛类型不同，使用不同的报名表单；
 * 进行活动报名
 */
public class BaoMingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baoming_activity);
        setTitleLeftBtn();
        setTitleName(R.string.xuanshoubaoming);
        initView();
    }

    private void initView() {

    }
}
