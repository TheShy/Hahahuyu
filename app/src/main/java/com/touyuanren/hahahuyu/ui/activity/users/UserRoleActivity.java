package com.touyuanren.hahahuyu.ui.activity.users;

import android.os.Bundle;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;

public class UserRoleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_role);

        setTitleLeftBtn();
        setTitleName(R.string.role);
    }

    
}
