package com.touyuanren.hahahuyu.audio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.MusicActivity;
import com.touyuanren.hahahuyu.utils.FoToast;

/**
 * Created by Administrator on 2017/7/20/020.
 */

public class SendAcitivity extends BaseActivity implements View.OnClickListener {


    private TextView send_tv_news, send_tv_audio, send_tv_vedio;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_activity);
        setTitleLeftBtn();
        setTitleName("发布");

        initView();

    }

    private void initView() {
        send_tv_news = (TextView) findViewById(R.id.send_tv_news);
        send_tv_audio = (TextView) findViewById(R.id.send_tv_audio);
        send_tv_vedio = (TextView) findViewById(R.id.send_tv_vedio);

        send_tv_news.setOnClickListener(this);
        send_tv_audio.setOnClickListener(this);
        send_tv_vedio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_tv_news:
                FoToast.showToast("暂未开放,敬请期待！");
                break;
            case R.id.send_tv_audio:
                Intent send_aduio = new Intent(SendAcitivity.this, MusicActivity.class);
                startActivity(send_aduio);
                break;
            case R.id.send_tv_vedio:
                FoToast.showToast("暂未开放,敬请期待！");
                break;
        }

    }
}
