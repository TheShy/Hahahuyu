package com.touyuanren.hahahuyu.ui.activity.shouye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.HdDetailMsgFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.HuoDongAlbumFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.ProgrammeFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.TopicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/8/23.
 * 最新的活动页面
 */
public class HuoDongDetailAct extends BaseActivity {
    /**
     * 活动详情fragment
     */
    private HdDetailMsgFrag mDetailMsg;
    /**
     * 红包墙fragment
     */
//    private HongBaoWallFrag mHongBaowallFrag;
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
     * 导航栏
     */
    private TabLayout mTablayout;
    /**
     * viewpager
     */
    private ViewPager mViewPager;
    /**
     * 活动更多详情数据
     */
    private List<Fragment> list = new ArrayList<>();
    /**
     * 传递过来的活动信息
     */
    private HuoDongInfo mHuoDongInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_huodong_detail);
        setTitleLeftBtn();
        setTitleName(R.string.huodong_detail);
        Intent mItent = getIntent();
        Bundle mBundle = mItent.getExtras();
        mHuoDongInfo = (HuoDongInfo) mBundle.get("huodongInfo");
        initView();
        initData();
    }

    private void initView() {
        mTablayout = (TabLayout) findViewById(R.id.tabLayout_huodong_frag);
        mViewPager = (ViewPager) findViewById(R.id.vp_huodong_detail_act);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), MyApplication.getContext()));
        //设置标题文字颜色
        mTablayout.setTabTextColors(R.color.white,R.color.color_999);
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        mDetailMsg = HdDetailMsgFrag.newInstance(mHuoDongInfo);
        mTopicFragment = TopicFragment.newInstance(mHuoDongInfo);
        mHuoDongAlbumFrag = new HuoDongAlbumFrag();
//        mHongBaowallFrag = new HongBaoWallFrag();
        mProgrammerFrag = new ProgrammeFrag();
        list.add(mDetailMsg);
        list.add(mTopicFragment);
        list.add(mHuoDongAlbumFrag);
//        list.add(mHongBaowallFrag);
//        list.add(mProgrammerFrag);
    }
    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public final int COUNT = 3;
        private String[] titles = new String[]{getString(R.string.huodong_detail), getString(R.string.topic_huodong), getString(R.string.album)};
        private Context context;

        public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
