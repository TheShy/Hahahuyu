package com.touyuanren.hahahuyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.account.LoginActivity;
import com.touyuanren.hahahuyu.utils.FoContents;
import com.touyuanren.hahahuyu.utils.FoPreference;


/**
 * Created by Jing on 2016/3/2.
 * 欢迎页
 */
public class WelcomeActivity extends BaseActivity {
    /**
     * 引导页图片请求是否成功
     */
    private boolean isGuideSuccess = true;

    /**
     * 是否请求网络
     */
    private boolean isHttpRequest = false;

    /**/
    private String guideStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=WelcomeActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        init();
        delayStart();
    }

    /**
     * 初始化welcomeview
     */
    private void init() {
        //welcome view
        ImageView imageView = (ImageView) findViewById(R.id.iv_welcome);

        imageView.setBackgroundResource(R.drawable.welcome);
        //        displayImage(FoContents.URL_WELCOM, imageView);
    }

    /**
     * 间隔2秒跳转
     */
    private void delayStart() {
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                /**
                 * 分两种情况
                 * 第一种:如果无网络,那么isHttpRequest为false直接三秒跳转到首页
                 * 第二种:如果有网络,由于网速快慢不定,且在欢迎页中不能等待时间过长,那么当isHttpRequest为
                 *      true时,再进行2秒延迟,总共5秒,这样如果,五秒内还不能请求成功那么跳转到首页,如果请求
                 *      成功isGuideSuccess为true那么判断guide引导图片是否更新,若更新,进入引导页,若 没有
                 *      更新跳转到首页
                 */
                toNextActivity();
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

    public void toNextActivity() {
        Intent mIntent=null;
        if(TextUtils.isEmpty(FoPreference.getString(FoContents.LOGIN_TOKEN))){
             mIntent = new Intent(this, LoginActivity.class);
        }else {
             mIntent = new Intent(this, MainActivity.class);
        }
        startActivity(mIntent);
        finish();
    }

}
