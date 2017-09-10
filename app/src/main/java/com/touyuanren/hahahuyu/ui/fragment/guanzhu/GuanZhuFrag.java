package com.touyuanren.hahahuyu.ui.fragment.guanzhu;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;
import com.touyuanren.hahahuyu.ui.fragment.DongTaiFrag;
import com.touyuanren.hahahuyu.ui.fragment.LogFrag;

/**
 * Created by Jing on 2016/10/27.
 */
public class GuanZhuFrag extends BaseFragment {
    /**
     * frag的总布局
     */
    private View mView;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    /**
     * 动态frag
     */
    private DongTaiFrag dongTaiFrag;
    /**
     * 日志frag
     */
    private LogFrag logFrag;

    @Override
    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.frag_guanzhu, null);
        mTabLayout = (TabLayout) mView.findViewById(R.id.tabLayout_guanzhu_frag);
        initView();
        return mView;
    }
    private void initView() {
        dongTaiFrag=new DongTaiFrag();
        logFrag=new LogFrag();
        mViewpager = (ViewPager) mView.findViewById(R.id.vp_guanzhu_frag);
        mViewpager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), MyApplication.getContext()));
        mTabLayout.setupWithViewPager(mViewpager);
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public final int COUNT = 2;
        private String[] titles = new String[]{getString(R.string.dongtai),getString(R.string.log)};
        private Context context;

        public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return dongTaiFrag;
            } else {
                return logFrag;
            }

        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }




}
