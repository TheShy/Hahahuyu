package com.touyuanren.hahahuyu.ui.fragment.news;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.SearchActivity;
import com.touyuanren.hahahuyu.ui.activity.news.ChannelActivity;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/7/7.
 * 新闻资讯fragment,用来展示活动和赛事想关新闻
 */
public class NewsFragment extends BaseFragment {
    /**
     * frag的总布局
     */
    private View mView;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private ArrayList<NewsMediaFrag> list = new ArrayList();

    private ZiMeiTiFrag ziMeiTiFrag;
    /**
     * 添加更多栏目
     */
    private ImageView ima_add;

    /**
     * 搜索键
     */
    private ImageView ima_search_shouye_frag;


    @Override
    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.frag_news, null);
        mTabLayout = (TabLayout) mView.findViewById(R.id.tabLayout_news_frag);
        initView();
        return mView;
    }

    private void initView() {
        ima_search_shouye_frag = (ImageView) mView.findViewById(R.id.ima_search_shouye_frag);
        ima_add = (ImageView) mView.findViewById(R.id.ima_more_columns);

        ziMeiTiFrag = new ZiMeiTiFrag();
        list.add(NewsMediaFrag.newInstance("36"));
        list.add(NewsMediaFrag.newInstance("66"));
        list.add(NewsMediaFrag.newInstance("67"));
        list.add(NewsMediaFrag.newInstance("70"));
        list.add(NewsMediaFrag.newInstance("71"));
        mViewpager = (ViewPager) mView.findViewById(R.id.vp_news_frag);
        mViewpager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), MyApplication.getContext()));
        mTabLayout.setupWithViewPager(mViewpager);

        ima_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行频道选择
                Intent mIntent = new Intent(getActivity(), ChannelActivity.class);
                startActivity(mIntent);
            }
        });
        /**
         * 搜索按键
         */
        ima_search_shouye_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(mIntent);
            }
        });
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public final int COUNT = 6;
        private String[] titles = new String[]{"自媒体","媒体资讯", "百家言论", "财经", "娱乐", "体育"};
        private Context context;

        public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return ziMeiTiFrag;
            } else {
                return list.get(position-1);
            }
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
