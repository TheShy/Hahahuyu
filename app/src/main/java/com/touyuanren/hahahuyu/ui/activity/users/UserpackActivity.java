package com.touyuanren.hahahuyu.ui.activity.users;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;

public class UserpackActivity extends BaseActivity {

    String zhanghuyue ;
    String yue_dongjie ;
    String zbi ;

    private TextView tv_up_yue , tv_up_zbi , tv_up_xianhua , tv_up_dongjiejine , tv_up_yajin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpack);

        setTitleLeftBtn();
        setTitleName(R.string.huodong_detail);
        setTitleRightBtn(R.string.user_pack_liushui, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserpackActivity.this, R.string.zanweikaitong,Toast.LENGTH_LONG).show();
            }
        });

        zhanghuyue = getIntent().getStringExtra("zhanghuyue");
        yue_dongjie = getIntent().getStringExtra("yue_dongjie");
        zbi = getIntent().getStringExtra("zbi");

        init();
        initData();
    }

    private void  init(){
        tv_up_yue = (TextView) findViewById(R.id.tv_up_yue);
        tv_up_zbi = (TextView) findViewById(R.id.tv_up_zbi);
        tv_up_xianhua = (TextView) findViewById(R.id.tv_up_xianhua);
        tv_up_dongjiejine = (TextView) findViewById(R.id.tv_up_dongjiejine);
        tv_up_yajin = (TextView) findViewById(R.id.tv_up_yajin);
    }

    private void initData() {
        tv_up_yue.setText(zhanghuyue);
        tv_up_dongjiejine.setText(yue_dongjie);
        tv_up_zbi.setText(zbi);
    }
}
