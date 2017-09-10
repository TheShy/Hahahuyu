package com.touyuanren.hahahuyu.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.touyuanren.hahahuyu.ui.fragment.ItemFragment;

/**
 * Created by Jing on 2016/3/14.
 * viewpager_indcator 的适配器
 */
public class TabPageIndicatorAdapter extends FragmentPagerAdapter {
    private String[] TITLE;

    public TabPageIndicatorAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabPageIndicatorAdapter(FragmentManager fm, String[] TITLE) {
        super(fm);
        this.TITLE = TITLE;
    }

    @Override
    public Fragment getItem(int position) {
        //新建一个Fragment来展示ViewPager item的内容，并传递参数
        Fragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString("arg", TITLE[position]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position % TITLE.length];
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }
}


