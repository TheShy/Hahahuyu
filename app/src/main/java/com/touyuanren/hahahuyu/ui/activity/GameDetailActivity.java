package com.touyuanren.hahahuyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.GameDetailFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.GameVedio;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.HongBaoWallFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.HuoDongAlbumFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.PlayerPaiHangFrag;
import com.touyuanren.hahahuyu.ui.fragment.huodongdetail.ZuoPinPaiHangFrag;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Jing on 2016/4/6.
 * 比赛详情界面
 */
public class GameDetailActivity extends BaseActivity {
    /**
     * 活动详情fragment
     */
    private GameDetailFrag mDetailMsg;
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
//    private TopicFragment mTopicFragment;
    /**
     * 选手排行Fragment
     */
    private PlayerPaiHangFrag mPlayerPaiHangFrag;
    /**
     * 作品排行
     */
    private ZuoPinPaiHangFrag mZuoPinFrag;
    /**
     * 比赛视频fragment
     */
    private GameVedio mGameVedio;
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
        setContentView(R.layout.game_detail_activity);
        setTitleLeftBtn();
        setTitleName(R.string.game_detail);
        setTitleRightImage(R.drawable.share_bottom, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FoToast.toast(MyApplication.getContext(), "fenxiang");
                share();
            }
        });
        Intent mItent = getIntent();
        Bundle mBundle = mItent.getExtras();
        mHuoDongInfo = (HuoDongInfo) mBundle.get("huodongInfo");
        initView();
        initData();
    }


    /**
     * Android原生分享功能
     */
    private void share() {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "f分享");
        share_intent = Intent.createChooser(share_intent, "分享");
        startActivity(share_intent);
    }

    private void initView() {
        mTablayout = (TabLayout) findViewById(R.id.tabLayout_game_frag);
        mViewPager = (ViewPager) findViewById(R.id.vp_game_detail_act);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), MyApplication.getContext()));
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        mDetailMsg = GameDetailFrag.newInstance(mHuoDongInfo);
        mHuoDongAlbumFrag = new HuoDongAlbumFrag();
        mHongBaowallFrag = new HongBaoWallFrag();
        mPlayerPaiHangFrag = new PlayerPaiHangFrag();
        mGameVedio = GameVedio.newInstance(mHuoDongInfo);
        mZuoPinFrag = ZuoPinPaiHangFrag.newInstance(mHuoDongInfo);

        list.add(mDetailMsg);
        list.add(mHuoDongAlbumFrag);
        list.add(mHongBaowallFrag);
        list.add(mPlayerPaiHangFrag);
        list.add(mZuoPinFrag);
        list.add(mGameVedio);
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private String[] titles = new String[]{getString(R.string.game_detail),  getString(R.string.album), getString(R.string.hongbao_wall), getString(R.string.player_paihang), getString(R.string.zuopinpaihang),getString(R.string.game_vedio)};
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
