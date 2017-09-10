package com.touyuanren.hahahuyu.ui.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.users.ChongzhiActivity;

/**
 * Created by Jing on 2016/7/5.
 * 用户资金管理
 */
public class ManageMoneyAct extends BaseActivity implements View.OnClickListener {
    /**
     * 充值
     */
    private LinearLayout ll_recharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_money_manage);
        setTitleLeftBtn();
        setTitleName(getString(R.string.manage_money));
        initView();
    }

    private void initView() {
        ll_recharge = (LinearLayout) findViewById(R.id.ll_recharge_act);
        //绑定监听
        ll_recharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent mIntent = null;
        switch (v.getId()) {
            case R.id.ll_recharge_act:
                mIntent = new Intent(ManageMoneyAct.this, ChongzhiActivity.class);
                startActivity(mIntent);
                break;

        }
    }
}
