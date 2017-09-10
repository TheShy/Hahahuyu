package com.touyuanren.hahahuyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.HuoDongInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.shouye.HuoDongDetailAct;
import com.touyuanren.hahahuyu.ui.adapter.HuoDongAdapter;
import com.touyuanren.hahahuyu.utils.DensityUtil;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Jing on 2016/3/2.
 * 展示赛事列表，根据分类进行切换；根据id不同进行跳转
 */
public class GameActivity extends BaseActivity {
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * 赛事列表
     */
    private ListView lv_game;
    /**
     * 每页的赛事数据集合
     */
    ArrayList<HuoDongInfo> mList;
    /**
     * 赛事列表总得数据集合
     */
    ArrayList<HuoDongInfo> mTotalList = new ArrayList<>();
    private HuoDongAdapter adapter;
    /**
     * 请求的页数
     */
    private int page = 1;
    /**
     * 请求列表的请求地址
     */
    private String listUrl = HttpApi.ROOT_PATH + HttpApi.SEARCH_PATH + "?" + "&page=";
    //加载更多的总布局
    RelativeLayout id_rl_loading;
    ProgressBar id_pull_to_refresh_load_progress;
    TextView id_pull_to_refresh_loadmore_text;
    final String pull_Load_More = "拖动加载";
    final String loading_Load_More = "加载中...";
    final String comp_Load_More = "加载完成";
    String nowNormalText = "";//存放当前footview显示的文字
    boolean isLastItem = false;//是否是最后一项

    boolean isLoading = false;//是否正在加载中
    boolean isComp = false;//标记一次是否加载完成
    /**
     * listview的底部布局
     */
    private View listview_footer_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_game);
        setTitleLeftBtn();
        setTitleName(R.string.game);
        lv_game = (ListView) findViewById(R.id.lv_game_act);
        initSwipeLayout();
        initData();
    }

    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        int size = DensityUtil.dip2pxComm(this, 25);
        // 第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, size);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListInfo(1);
            }
        });
    }

    private void initData() {
        listview_footer_view = LayoutInflater.from(this).inflate(R.layout.listview_footer, null);
        id_rl_loading = (RelativeLayout) listview_footer_view.findViewById(R.id.id_rl_loading);
        id_pull_to_refresh_load_progress = (ProgressBar) listview_footer_view.findViewById(R.id.id_pull_to_refresh_load_progress);
        id_pull_to_refresh_load_progress.setVisibility(View.GONE);
        id_pull_to_refresh_loadmore_text = (TextView) listview_footer_view.findViewById(R.id.id_pull_to_refresh_loadmore_text);
        id_pull_to_refresh_loadmore_text.setClickable(false);
        lv_game.addFooterView(listview_footer_view);
        lv_game.setOnScrollListener(OnScrollListenerOne);
        nowNormalText = pull_Load_More;
        showLoading();
        getListInfo(1);
        lv_game.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = null;
                if ("0".equals(mTotalList.get(position).getMark())) {
                    //活动
                    mIntent = new Intent(GameActivity.this, HuoDongDetailAct.class);
                } else {
                    mIntent = new Intent(GameActivity.this, GameDetailActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("huodongInfo", mTotalList.get(position));
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });
    }

    //获取赛事信息
    private void getListInfo(final int page) {

        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("mark", "1");
        FoHttp.getMsg(listUrl + page, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            HuoDongInfoBean mHuoDongInfoBean = new Gson().fromJson(response, HuoDongInfoBean.class);
                            mList = (ArrayList<HuoDongInfo>) mHuoDongInfoBean.getData().getList();
                            Log.e("mList", mList.size() + "");
                            if (page == 1) {
                                mTotalList.clear();
                                mTotalList.addAll(mList);
                                adapter = new HuoDongAdapter(MyApplication.getContext(), mTotalList);
                                lv_game.setAdapter(adapter);
                                //如果小于十条不进行上拉加载
                                if (mList.size() < 10) {
                                    lv_game.removeFooterView(listview_footer_view);
                                }
                                swipeRefreshLayout.setRefreshing(false);
                            } else if (mList.size() <= 0) {
                                //没有了，对底部进行处理
                                id_pull_to_refresh_loadmore_text.setText("没有更多了");
                                id_pull_to_refresh_loadmore_text.setClickable(false);
                                id_pull_to_refresh_load_progress.setVisibility(View.GONE);
                                isComp = true;
                                lv_game.removeFooterView(id_rl_loading);
                            } else {
                                isLoading = false;
                                //再次对listview的footview进行处理
                                mTotalList.addAll(mList);
                                adapter.notifyDataSetChanged();
                                id_pull_to_refresh_loadmore_text.setText(nowNormalText);
                                id_pull_to_refresh_load_progress.setVisibility(View.GONE);
                                isComp = false;
                                id_pull_to_refresh_loadmore_text.setClickable(true);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hideLoading();
            }
        });
    }

    /**
     * 上拉加载1
     */
    AbsListView.OnScrollListener OnScrollListenerOne = new AbsListView.OnScrollListener() {
        //正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
        //回调顺序如下
        //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
        //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
        //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当滚到最后一行且停止滚动时，执行加载
            if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && !isLoading) {
                //加载元素
                loadMore();
                isLastItem = false;
            }
        }

        //滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
        //firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
        //visibleItemCount：当前能看见的列表项个数（小半个也算）
        //totalItemCount：列表项共数
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //判断是否滚到最后一行
            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                isLastItem = true;
            }
        }
    };

    //加载更多
    private void loadMore() {
        id_rl_loading.setVisibility(View.VISIBLE);
        id_pull_to_refresh_loadmore_text.setText(loading_Load_More);
        id_pull_to_refresh_loadmore_text.setClickable(false);
        id_pull_to_refresh_load_progress.setVisibility(View.VISIBLE);
        isLoading = true;
        page++;
        getListInfo(page);
    }
}
