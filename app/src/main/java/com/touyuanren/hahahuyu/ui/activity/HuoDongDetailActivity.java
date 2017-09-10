package com.touyuanren.hahahuyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.HdDetailMsgFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.HongBaoWallFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.HuoDongAlbumFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.ProgrammeFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.TopicFragment;
import com.touyuanren.hahahuyu.utils.FoToast;

/**
 * Created by Jing on 2016/1/28.
 * 活动详情界面;暂时废弃
 */
public class HuoDongDetailActivity extends BaseActivity implements View.OnClickListener {
    private ScrollView mScrollView;
    /**
     * 记录是否收藏，如果收藏为1，不收藏为0；默认为0
     */
    private int isCollect = 0;
    /**
     * 收藏
     */
    private TextView collectText;
    /**
     * 点击购票
     */
    private TextView buyTicket;
    /**
     * 活动地址
     */
    private TextView addressActivity;
    /**
     * 活动详情fragment
     */
    private HdDetailMsgFrag mDetailMsg;
    /**
     * 红包墙fragment
     */
    private HongBaoWallFrag mHongBaowallFrag;
    /**
     * 活动相册
     */
    private HuoDongAlbumFrag mHuoDongAlbumFrag;
    /**
     * 话题fragment
     */
    private TopicFragment mTopicFragment;
    /**
     * 节目单Fragment
     */
    private ProgrammeFrag mProgrammerFrag;
    /**
     * 底部导航栏
     */
    private RadioGroup mRadioGroup;
    /**
     * 活动更多详情数据
     */
    private String huoDongDetaildata = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huodongdetail_activity);
        setTitleLeftBtn();
        setTitleName(R.string.huodong_detail);
        setTitleRightImage(R.drawable.share_bottom, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoToast.toast(MyApplication.getContext(), "fenxiang");
            }
        });
        initView();
        initData();
    }

    private void initData() {
        //初始化5个fragment;使用tab进行切换

    }

    private void initView() {
        addressActivity = (TextView) findViewById(R.id.address_activity_detail);
        collectText = (TextView) findViewById(R.id.collect_detail_activity);
        buyTicket = (TextView) findViewById(R.id.buy_ticket_detail);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_huodong_detail);
        mScrollView= (ScrollView) findViewById(R.id.scrollview_huodong_detail);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                mScrollView.fullScroll(ScrollView.FOCUS_UP);
                changeFragment(checkedId);
            }
        });

        ((RadioButton) mRadioGroup.findViewById(R.id.rb_huodongmsg)).setChecked(true);
        //绑定监听
        buyTicket.setOnClickListener(this);
        addressActivity.setOnClickListener(this);
        collectText.setOnClickListener(this);
        //监听scrollview滑动的距离
        
    }


    //实现对控件点击
    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            case R.id.address_activity_detail:
//                mIntent = new Intent(this, BaseMapActivity.class);
//                startActivity(mIntent);
                break;
            case R.id.collect_detail_activity:
                if (isCollect == 0) {
                    collectText.setSelected(true);
                    FoToast.toast(MyApplication.getContext(), "已收藏");
                    isCollect = 1;
                } else {
                    collectText.setSelected(false);
                    FoToast.toast(MyApplication.getContext(), "已取消收藏");
                    isCollect = 0;
                }
                break;
            //点击进行购票
            case R.id.buy_ticket_detail:
                mIntent = new Intent(HuoDongDetailActivity.this, BuyTicketActivity.class);
                startActivity(mIntent);
                break;

        }
    }

    /**
     * 通过点击button切换fragment，
     *
     * @param checkedId：点击的button的id
     */
    private void changeFragment(int checkedId) {
        //Fragment事物对象
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        hideAllFragment(ft);

        /**
         * 每次Fragment切换时都需要实时修改标题内容
         */
        switch (checkedId) {
            case R.id.rb_huodongmsg:
                if (null == mDetailMsg) {
                    mDetailMsg = new HdDetailMsgFrag();
                    Bundle mBundle = new Bundle();
                    mBundle.putString("detailmsg", huoDongDetaildata);
                    mDetailMsg.setArguments(mBundle);
                }

                if (mDetailMsg.isAdded()) {
                    ft.show(mDetailMsg);
                } else {
                    ft.add(R.id.fl_huodogn_detail_content, mDetailMsg);
                }
                break;

            case R.id.rb_topic_huodong:
                if (null == mTopicFragment) {
                    mTopicFragment = new TopicFragment();
                }

                if (mTopicFragment.isAdded()) {
                    ft.show(mTopicFragment);
                } else {
                    ft.add(R.id.fl_huodogn_detail_content, mTopicFragment);
                }
                break;
            case R.id.rb_album_huodong:
                if (null == mHuoDongAlbumFrag) {
                    mHuoDongAlbumFrag = new HuoDongAlbumFrag();
                }

                if (mHuoDongAlbumFrag.isAdded()) {
                    ft.show(mHuoDongAlbumFrag);
                } else {
                    ft.add(R.id.fl_huodogn_detail_content, mHuoDongAlbumFrag);
                }
                break;

            case R.id.rb_hongbaoWall_huodong:
                if (null == mHongBaowallFrag) {
                    mHongBaowallFrag = new HongBaoWallFrag();
                }

                if (mHongBaowallFrag.isAdded()) {
                    ft.show(mHongBaowallFrag);
                } else {
                    ft.add(R.id.fl_huodogn_detail_content, mHongBaowallFrag);
                }
                break;
            case R.id.rb_programme_huodong:
                if (null == mProgrammerFrag) {
                    mProgrammerFrag = new ProgrammeFrag();
                }

                if (mProgrammerFrag.isAdded()) {
                    ft.show(mProgrammerFrag);
                } else {
                    ft.add(R.id.fl_huodogn_detail_content, mProgrammerFrag);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 在切换Fragment时隐藏所有的Fragment
     */

    private void hideAllFragment(FragmentTransaction ft) {
        if (null != ft) {
            if (null != mHuoDongAlbumFrag) {
                ft.hide(mHuoDongAlbumFrag);
            }

            if (null != mTopicFragment) {
                ft.hide(mTopicFragment);
            }

            if (null != mDetailMsg) {
                ft.hide(mDetailMsg);
            }
            if (null != mHongBaowallFrag) {
                ft.hide(mHongBaowallFrag);
            }
            if (null != mProgrammerFrag) {
                ft.hide(mProgrammerFrag);
            }
        }
    }
}
