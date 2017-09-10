package com.touyuanren.hahahuyu.ui.activity.account;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.fragment.MyFriendsFrag;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jing on 2016/8/19.
 */
public class MyFriendsAct extends BaseActivity {
    @BindView(R.id.tabLayout_friends_act)

    TabLayout tabLayoutFriendsAct;
    @BindView(R.id.vp_friends_act)
    ViewPager vpFriendsAct;
    private ArrayList<Fragment> list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_myfriends);
        ButterKnife.bind(this);
        setTitleLeftBtn();
        setTitleName(R.string.friends);
        initView();
    }
    private void initView() {
        MyFriendsFrag  myFriendsFrag=new MyFriendsFrag();
        list.add(MyFriendsFrag.newInstance("71"));
        list.add(MyFriendsFrag.newInstance("71"));
        vpFriendsAct.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), MyApplication.getContext()));
        tabLayoutFriendsAct.setupWithViewPager(vpFriendsAct);
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public final int COUNT = 2;
        private String[] titles = new String[]{"聊天","好友"};
        private Context context;

        public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return   list.get(position);
//            if (position == 0) {
//                return ziMeiTiFrag;
//            } else {
//                return list.get(position-1);
//            }
//            return PageFragment.newInstance(position + 1);
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
