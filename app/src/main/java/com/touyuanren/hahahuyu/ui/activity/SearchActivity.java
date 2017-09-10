package com.touyuanren.hahahuyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/2/2.
 * 主要实现搜索功能，通过标签和标题两方面进行搜索；从网络端获取标签，进行保存，显示，
 * 每次输入搜索时进行保存搜索内容，如果gridview显示不合适，则使用listview
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 显示标签
     */
    private GridView labelGridView;
    /**
     * 返回键
     */
    private ImageView backIma;
    /**
     * 保存活动主题标签列表
     */
    private ArrayList<String> activityTopicList = new ArrayList<String>();
    /**
     * gridview 的适配器
     */
    private ArrayAdapter arr_adapter;
    /**
     * 标签数据
     */
    private String[] label;
    /**
     * 搜索
     */
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        initView();
        initData();
        initArrayAdapter(MyApplication.getContext(), activityTopicList);
        labelGridView.setAdapter(arr_adapter);
    }

    private void initView() {
        labelGridView = (GridView) findViewById(R.id.grid_search_activity);
        backIma = (ImageView) findViewById(R.id.back_search_activity);
        mSearchView = (SearchView) findViewById(R.id.search_hd);
        //绑定监听
        backIma.setOnClickListener(this);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //进行查询
                Intent mIntent=new Intent(SearchActivity.this,SearchListAct.class);
                mIntent.putExtra("keyword",query);
                startActivity(mIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void initData() {
        //初始化活动主题集合
        activityTopicList.add("创业");
        activityTopicList.add("商务");
        activityTopicList.add("公益");
        activityTopicList.add("社交");
        activityTopicList.add("亲子");
        activityTopicList.add("电影");
        activityTopicList.add("娱乐");
        activityTopicList.add("生活");
        activityTopicList.add("音乐");
        activityTopicList.add("科技");
        activityTopicList.add("运动");
        activityTopicList.add("课程");
        activityTopicList.add("校园");
        activityTopicList.add("文化");
        activityTopicList.add("其他");
//        label = new String[]{"创业", "商务", "公益", "社交", "亲子", "电影", "娱乐", "生活",
//                "音乐", "科技", "运动", "课程", "校园", "文化", "其他"};
    }

    public void initArrayAdapter(Context context, ArrayList<String> list) {
        arr_adapter = new ArrayAdapter(context, R.layout.mb_search_gridview, list);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_search_activity:
                finish();
                break;
        }
    }


}
