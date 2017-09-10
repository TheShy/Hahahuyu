package com.touyuanren.hahahuyu.ui.activity.account;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.utils.SwitchDpOrPx;

/**
 * Created by Jing on 2016/2/16.
 * 管理相关的票
 */
public class ManageTicketActivity extends BaseActivity implements View.OnClickListener {
    /**
     * radiogroup实现单选
     */
    private RadioGroup mRadioGroup;
    /**
     * 屏幕的宽度
     */
    private int w_screen;
    /**
     * imageview,滑动的线
     */
    private ImageView lineLeftScroll;
    /**
     * 返回
     */
    private ImageView back;
    /**
     * imageview,滑动的线
     */
    private ImageView lineRightScroll;
    /**
     * 位移动画
     */
    private TranslateAnimation animation;
    /**
     * viewpager,实现切换
     */
    private ViewPager mViewPager;
    /**
     * 屏幕的高度
     */
    private int h_screen;
    /**
     * 定义一个变量，来判断是否是第一次进入这个activity
     */
    private int number = 0;
    //屏幕剩余的宽度
    private int widthScroll;
    //屏幕左侧返回键的宽度
    private int widthBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_ticket);
        initData();
        initView();
    }

    //初始化控件
    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_ticket_activity);
        lineLeftScroll = (ImageView) findViewById(R.id.line_left_ticketactivity);
        lineRightScroll = (ImageView) findViewById(R.id.line_right_ticketactivity);
        mViewPager = (ViewPager) findViewById(R.id.pager_activity_managerticket);
        back = (ImageView) findViewById(R.id.back_manageticket_activity);
        //绑定监听
        back.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeTab(checkedId);
            }
        });
        ((RadioButton) mRadioGroup.findViewById(R.id.myticket_activity)).setChecked(true);
    }

    //tab进行切换
    public void changeTab(int checkedId) {
        switch (checkedId) {
            case R.id.myticket_activity:
                ((RadioButton) mRadioGroup.findViewById(R.id.myticket_activity)).setChecked(true);
                lineLeftScroll.setVisibility(View.VISIBLE);
                if (number == 0) {
                    number = 1;
                } else {
                    animation = new TranslateAnimation(widthBack + widthScroll, widthBack, 0, 0);
                    animation.setDuration(1000);
                    animation.setFillAfter(true);
                    lineLeftScroll.setAnimation(animation);
                    animation.startNow();
                }
                lineRightScroll.setVisibility(View.INVISIBLE);
                break;
            case R.id.outdata_ticket_activity:
                ((RadioButton) mRadioGroup.findViewById(R.id.outdata_ticket_activity)).setChecked(true);
                TranslateAnimation animation = new TranslateAnimation(widthBack, widthBack + widthScroll, 0, 0);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                lineLeftScroll.setAnimation(animation);
                animation.startNow();
                lineLeftScroll.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void initData() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        w_screen = dm.widthPixels;
        h_screen = dm.heightPixels;
        widthBack = SwitchDpOrPx.dip2px(MyApplication.getContext(), 20);
        widthScroll = (w_screen - widthBack) / 2;
    }

    //实现监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_manageticket_activity:
                finish();
                break;
        }
    }
}
