package com.touyuanren.hahahuyu.ui.activity.account;

import android.os.Bundle;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;

/**
 * Created by Jing on 2016/7/26.
 */
public class CommentActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_comment);
        setTitleLeftBtn();
        setTitleName(R.string.comment);

    }
}
