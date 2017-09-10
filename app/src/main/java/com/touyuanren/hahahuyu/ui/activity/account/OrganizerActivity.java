package com.touyuanren.hahahuyu.ui.activity.account;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.adapter.TabPageIndicatorAdapter;


/**
 * Created by Jing on 2016/3/14.
 * 主办方界面
 */
public class OrganizerActivity extends BaseActivity implements View.OnClickListener {
    private TextView introduceCompany;   //文本域;公司简介
    private ImageView mImageView;  //翻转icon
    int maxLine = 1;  //TextView设置默认最大展示行数为5


    /**
     * Tab标题
     */
    private static final String[] TITLE = new String[]{"过去活动", "未来活动"};
    /**
     * 主办方简介
     */
    private String introduce;
    /**
     * viewpager的适配器
     */
    private FragmentPagerAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_activity);
        setTitleLeftBtn();
        setTitleName(R.string.app_name);
        initView();
        initData();

    }

    private void initData() {
            //从网络端获取公司简介
        introduce = "陌陌互娱是北京大视线科技文化旗下的赛事/活动发布、展示、互动、互助平台。陌陌互娱可以为用" +
                "户提供线上赛事和活动的发布、传播、报名、支付、互动、签到、实时动态发布等" +
                "功能，为活动或赛事的主办方提供从报名到互动到资源对接到任务协作的一站式信息化解决方案。";
        introduceCompany.setText(introduce);//设置文本内容
        //ViewPager的adapter
        vpAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), TITLE);

    }

    private void initView() {
        introduceCompany = (TextView) findViewById(R.id.adjust_text);
        mImageView = (ImageView) findViewById(R.id.turn_over_icon);
        introduceCompany.setHeight(introduceCompany.getLineHeight() * maxLine);  //设置默认显示高度
        //根据高度来控制是否展示翻转icon
        introduceCompany.post(new Runnable() {
            @Override
            public void run() {
                mImageView.setVisibility(introduceCompany.getLineCount() > maxLine ? View.VISIBLE : View.GONE);
            }
        });
        mImageView.setOnClickListener(new MyTurnListener());  //翻转监听
    }

    //实现监听
    @Override
    public void onClick(View v) {

    }

    private class MyTurnListener implements View.OnClickListener {

        boolean isExpand;  //是否翻转

        @Override
        public void onClick(View v) {
            isExpand = !isExpand;
            introduceCompany.clearAnimation();  //清除动画
            final int tempHight;
            final int startHight = introduceCompany.getHeight();  //起始高度
            int durationMillis = 200;

            if (isExpand) {
                /**
                 * 折叠效果，从长文折叠成短文
                 */

                tempHight = introduceCompany.getLineHeight() * introduceCompany.getLineCount() - startHight;  //为正值，长文减去短文的高度差
                //翻转icon的180度旋转动画
                RotateAnimation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(durationMillis);
                animation.setFillAfter(true);
                mImageView.startAnimation(animation);
            } else {
                /**
                 * 展开效果，从短文展开成长文
                 */
                tempHight = introduceCompany.getLineHeight() * maxLine - startHight;//为负值，即短文减去长文的高度差
                //翻转icon的180度旋转动画
                RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(durationMillis);
                animation.setFillAfter(true);
                mImageView.startAnimation(animation);
            }


            Animation animation = new Animation() {
                //interpolatedTime 为当前动画帧对应的相对时间，值总在0-1之间
                protected void applyTransformation(float interpolatedTime, Transformation t) { //根据ImageView旋转动画的百分比来显示textview高度，达到动画效果
                    introduceCompany.setHeight((int) (startHight + tempHight * interpolatedTime));//原始长度+高度差*（从0到1的渐变）即表现为动画效果

                }
            };
            animation.setDuration(durationMillis);
            introduceCompany.startAnimation(animation);

        }
    }
}
